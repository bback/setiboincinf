package boincinf.gui.actions;

import org.eclipse.jface.action.*;

import boincinf.gui.*;
import boincinf.netstat.*;

/**
 * @author kgraul
 */
public class CreditUpdateOverviewAction extends Action {
    BoincInfGui gui;
    SQLStorage sqlStore;

    public CreditUpdateOverviewAction(BoincInfGui g, SQLStorage store) {
        gui = g;
        sqlStore = store;
        setText("Show credit update overview@Ctrl+V");
        setToolTipText("Show credit update overview");
        //setImageDescriptor(Util.getDescriptor("config"));
    }

    public void run() {
        CreditUpdateOverviewDialog optdlg = new CreditUpdateOverviewDialog(gui, sqlStore);
        optdlg.open();
    }

}
