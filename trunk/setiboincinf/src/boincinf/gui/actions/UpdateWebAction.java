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

import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.operation.*;

import boincinf.*;
import boincinf.gui.*;
import boincinf.netstat.*;

public class UpdateWebAction extends Action {
    BoincInfGui window;
    SQLStorage store;

    public UpdateWebAction(final BoincInfGui s, final SQLStorage st) {
        window = s;
        store = st;
        setText("Update from website@Ctrl+U");
        setToolTipText("Update from website");
        setImageDescriptor(Util.getDescriptor("update"));
    }

    @Override
    public void run() {
        if (BoincNetStats.getCfg().getStrProperty("accountid").length() == 0 ) {
            MessageDialog.openError(
                window.getShell(),
                "Incomplete configuration",
                "No account ID is configured. Go to the options menu to set one.");
            BoincNetStats.out("Error: No account id configured.");
            return;
        }

        final WorkingProcess worker = new WorkingProcess(store);
        final ProgressMonitorDialog dialog = new ProgressMonitorDialog(window.getShell());
        try {
            dialog.run(true, true, worker);
        } catch (final Exception e) { }

        final HostStats hs = worker.getHostStats();

        if( hs==null && worker.getError() == null ) {
            // runUpdate returned no error, but also no result -> means we run
            // with 'update only on new data' and there were no new data
            return;
        }
        if (hs == null) {
            String msg;
            if (worker.getError() != null) {
                msg = "Error retrieving website:\n" + worker.getError().getMessage();
            } else {
                msg = "Error retrieving website.";
            }
            MessageDialog.openError(window.getShell(), "Retrieving website", msg);
            BoincNetStats.out("ERROR retrieving website");
            return;
        }

        window.getShell().getDisplay().asyncExec(new Runnable() {
            public void run() {
                window.addHostStat(hs);
                window.selectFirstHostStat();
            }
        });
    }

    private class WorkingProcess implements IRunnableWithProgress {
        private HostStats hoststats = null;
        private final SQLStorage targetstore;
        private Exception error = null;
        public Exception getError() {
            return error;
        }
        public HostStats getHostStats() {
            return hoststats;
        }
        public WorkingProcess(final SQLStorage s) {
            targetstore = s;
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
            if (getHostStats() == null) {
                monitor.setTaskName("Update failed.");
            } else {
                monitor.setTaskName("Finished update.");
            }
            monitor.done();
        }

        private int getTaskCount() {
            return 2;
        }

        private void processTask(final int i) throws InvocationTargetException, InterruptedException {
            if (i == 1) {
                try {
                    hoststats = BoincNetStats.runUpdate(targetstore);
                } catch (final Exception e) {
                    error = e;
                }
            }
        }
    }

}
