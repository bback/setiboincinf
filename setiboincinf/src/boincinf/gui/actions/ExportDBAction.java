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

public class ExportDBAction extends Action {
    ApplicationWindow window;
    SQLStorage store;

    public ExportDBAction(final ApplicationWindow w, final SQLStorage st) {
        window = w;
        store = st;
        setText("Export database@Ctrl+E");
        setToolTipText("Export a database");
        setImageDescriptor(Util.getDescriptor("export"));
    }

    @Override
    public void run() {
        final Shell shell = window.getShell();
        final ExportDBInputDialog dlg = new ExportDBInputDialog(shell);
        dlg.open();
        final String dbname = dlg.getDatabaseName();
        if (dbname == null) {
            return; // user cancelled
        }
        BoincNetStats.out("Exporting database '" + dbname + "'");

        SQLStorage exportstore = null;
        try {
            exportstore = SQLStorage.createSQLStore(dbname);
            try {
                exportstore.createDbTables();
            } catch (final Exception e) {
                MessageDialog.openError(
                    shell,
                    "Creating table",
                    "Error creating table, maybe export database already exists?");
                BoincNetStats.out(
                    "Error creating table, maybe export database already exists?\n" + e.toString());
                return;
            }

            final List<HostStats> l = store.getHostStats();
            final WorkingProcess worker = new WorkingProcess(l, store, exportstore);
            final ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
            dialog.run(true, true, worker);

            exportstore.close();

            final String o = "Ready, exported " + l.size() + " statistic entries.";
            MessageDialog.openInformation(shell, "Export completed", o);
            BoincNetStats.out(o);
        } catch (final Throwable t) {
            MessageDialog.openError(shell, "Error exporting database", t.toString());
            BoincNetStats.out("Error exporting database: " + t.toString());
            return;
        }
    }

    private static class WorkingProcess implements IRunnableWithProgress {
        List<HostStats> hoststats;
        SQLStorage exportstore;
        SQLStorage store;

        public WorkingProcess(final List<HostStats> hs, final SQLStorage s, final SQLStorage es) {
            hoststats = hs;
            exportstore = es;
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
            final String o = "Ready, exported " + getTaskCount() + " statistic entries.";
            monitor.setTaskName(o);
            monitor.done();
        }

        private int getTaskCount() {
            return hoststats.size();
        }

        private void processTask(final int i) throws InvocationTargetException, InterruptedException {
            final HostStats hs = hoststats.get(i);
            hs.singleHosts = store.retrieveSingleHosts(hs.timestamp);
            exportstore.saveToSQL(hs);
        }
    }
}
