package boincinf.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

import boincinf.BoincNetStats;
import boincinf.gui.*;
import boincinf.netstat.SQLStorage;

public class ExitAction extends Action {
    ApplicationWindow window;
    SQLStorage store;

    public ExitAction(ApplicationWindow w, SQLStorage st) {
        window = w;
        store = st;
        setText("Exit@Alt+F4");
        setToolTipText("Exit the application");
        setImageDescriptor(Util.getDescriptor("close"));
    }

    public void run() {
        ((BoincInfGui)window).saveStateToConfig();
        BoincNetStats.getCfg().saveConfig();
        store.close();
        window.close();
    }
}
