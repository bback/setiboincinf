package boincinf.gui.actions;

import org.eclipse.jface.action.Action;

import boincinf.BoincNetStats;
import boincinf.gui.BoincInfGui;
import boincinf.netstat.SQLStorage;

public class UpdateDBAction extends Action {
    BoincInfGui window;
    SQLStorage store;

    public UpdateDBAction(BoincInfGui s, SQLStorage st) {
        window = s;
        store = st;
        setText("Update from updateloop database");
        setToolTipText("Update from updateloop database");
//        setImageDescriptor(Util.getDescriptor("update"));
    }

    public void run() {
        try {
            window.autoDatabaseUpdate(true);
        } catch(Exception ex) {
            BoincNetStats.out("Error: "+ex.toString());
        }
    }
}
