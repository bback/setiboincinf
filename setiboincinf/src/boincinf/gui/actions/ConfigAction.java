package boincinf.gui.actions;

import org.eclipse.jface.action.Action;

import boincinf.BoincNetStats;
import boincinf.gui.*;

public class ConfigAction extends Action {
    BoincInfGui gui;

    public ConfigAction(BoincInfGui g) {
        gui = g;
        setText("Options@Ctrl+O");
        setToolTipText("Show options dialog");
        setImageDescriptor(Util.getDescriptor("config"));
    }

    public void run() {
        NiceOptionsDialog optdlg = new NiceOptionsDialog(gui, BoincNetStats.getCfg());
        optdlg.open();
    }
}
