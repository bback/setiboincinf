package boincinf.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;

import boincinf.BoincNetStats;

public class AboutAction extends Action {
    ApplicationWindow window;

    public AboutAction(ApplicationWindow w) {
        window = w;
        setText("About ...");
        setToolTipText("About the program");
//        setImageDescriptor(Util.getDescriptor("config"));
    }

    public void run() {
        String txt = "SetiBoincInf  "+BoincNetStats.VERSION+"\n";
        txt += "http://setiboincinf.bback.de\n\n";
        txt += "For help check the HTML docs in doc/ folder.";
        
        MessageDialog.openInformation(window.getShell(), "About ...", txt);
    }
}
