package boincinf.gui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.operation.IRunnableWithProgress;

import boincinf.BoincNetStats;
import boincinf.gui.*;
import boincinf.netstat.*;

public class UpdateWebAction extends Action {
    BoincInfGui window;
    SQLStorage store;

    public UpdateWebAction(BoincInfGui s, SQLStorage st) {
        window = s;
        store = st;
        setText("Update from website@Ctrl+U");
        setToolTipText("Update from website");
        setImageDescriptor(Util.getDescriptor("update"));
    }

    public void run() {
        if (BoincNetStats.getCfg().getStrProperty("accountid").length() == 0 ) {
            MessageDialog.openError(
                window.getShell(),
                "Incomplete configuration",
                "No account ID is configured. Go to the options menu to set one.");
            BoincNetStats.out("Error: No account id configured.");
            return;
        }
        
        WorkingProcess worker = new WorkingProcess(store);
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(window.getShell());
        try {
            dialog.run(true, true, worker);
        } catch (Exception e) { }
        
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
        private SQLStorage targetstore;
        private Exception error = null;
        public Exception getError() {
            return error;
        }
        public HostStats getHostStats() {
            return hoststats;
        }
        public WorkingProcess(SQLStorage s) {
            targetstore = s;
        }
        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            int size = getTaskCount();
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

        private void processTask(int i) throws InvocationTargetException, InterruptedException {
            if (i == 1) {
                try {
                    hoststats = BoincNetStats.runUpdate(targetstore);
                } catch (Exception e) {
                    error = e;
                }
            }
        }
    }

}
