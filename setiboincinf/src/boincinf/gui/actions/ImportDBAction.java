/*
  Copyright (C) 2008  SetiBoincInf Project

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License as
  published by the Free Software Foundation; either version 2 of
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/
package boincinf.gui.actions;

import java.lang.reflect.*;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.operation.*;
import org.eclipse.jface.window.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.gui.*;
import boincinf.netstat.*;

public class ImportDBAction extends Action {
    ApplicationWindow window;
    Shell shell;
    BoincInfGui gui;
    SQLStorage store;

    public ImportDBAction(final BoincInfGui g, final SQLStorage st) {
        gui = g;
        store = st;
        setText("Import database@Ctrl+I");
        setToolTipText("Import a database");
        setImageDescriptor(Util.getDescriptor("import"));
    }

    @Override
    public void run() {
        final ImportDBInputDialog dlg = new ImportDBInputDialog(shell);
        dlg.open();
        final String dbname = dlg.getDatabaseName();
        if (dbname == null) {
            return; // user cancelled
        }
        BoincNetStats.out("Importing database '" + dbname + "'");

        SQLStorage importstore = null;
        try {
            List<HostStats> l = null;
            try {
                importstore = SQLStorage.createSQLStore(dbname);
                l = importstore.importHostStats();
                if (l.size() == 0) {
                    final String o = "No database entries found, maybe import database does not exist?";
                    MessageDialog.openError(shell, "Import database empty", o);
                    BoincNetStats.out(o);
                    return;
                }
            } catch (final Exception t) {
                MessageDialog.openError(shell, "Open of import database failed", t.toString());
                BoincNetStats.out("Error opening import database: " + t.toString());
                return;
            }

            final WorkingProcess worker = new WorkingProcess(l, store, importstore);
            final ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
            dialog.run(true, true, worker);

            importstore.close();

            final String o = "Ready, imported " + worker.getImported() + " of " + l.size() + " statistic entries.";
            MessageDialog.openInformation(shell, "Import completed", o);
            BoincNetStats.out(o);

        } catch (final Throwable t) {
            MessageDialog.openError(shell, "Error importing database", t.toString());
            BoincNetStats.out("Error importing database: " + t.toString());
        }
        gui.reloadHostStats();
    }

    private static class WorkingProcess implements IRunnableWithProgress {
        List<HostStats> hoststats;
        SQLStorage importstore;
        SQLStorage store;
        int imported = 0;

        public WorkingProcess(final List<HostStats> hs, final SQLStorage s, final SQLStorage is) {
            hoststats = hs;
            importstore = is;
            store = s;
        }
        public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            final int size = getTaskCount();
            monitor.beginTask("Start", size);
            for (int i = 0; i < size; i++) {
                if (monitor.isCanceled()) {
                    monitor.setTaskName("Canceled: " + i);
                    monitor.done();
                    return;
                }
                processTask(i);
                monitor.setTaskName("Proceed: " + i);
                monitor.worked(1);
            }
            final String o = "Ready, imported " + getImported() + " of " + getTaskCount() + " statistic entries.";
            monitor.setTaskName(o);
            monitor.done();
        }

        private int getTaskCount() {
            return hoststats.size();
        }

        public int getImported() {
            return imported;
        }

        private void processTask(final int i) throws InvocationTargetException, InterruptedException {
            final HostStats hs = hoststats.get(i);

            // check if we should insert this entry
            final int diffhour = 1; // +/- 1 hour

            final long diff = (diffhour * 60l * 60l * 1000l);
            final long checklower = hs.timestamp.getTime() - diff;
            final long checkupper = hs.timestamp.getTime() + diff;
            final java.sql.Timestamp tslower = new java.sql.Timestamp(checklower);
            final java.sql.Timestamp tsupper = new java.sql.Timestamp(checkupper);

            final boolean doInsert = store.existRecordsInRange(tslower, tsupper);
            if (doInsert == false) {
                hs.singleHosts = importstore.retrieveSingleHosts(hs.timestamp);
                hs.sumSingleHosts(); // always compute new sums
                store.saveToSQL(hs);
                imported++;
            }
        }
    }
}
