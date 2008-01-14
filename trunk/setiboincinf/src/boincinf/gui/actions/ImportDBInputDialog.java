package boincinf.gui.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ImportDBInputDialog extends org.eclipse.jface.dialogs.Dialog {
    Shell shell;
    Text txt1;
    String dbname;

    public ImportDBInputDialog(Shell s) {
        super(s);
        shell = s;
    }

    public String getDatabaseName() {
        return dbname;
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite)super.createDialogArea(parent);

        Label txtlab1 = new Label(composite, SWT.NONE);
        txtlab1.setText("Database name");

        txt1 = new Text(composite, SWT.BORDER);
        txt1.setText("");

        GridLayout gl = new GridLayout();
        gl.marginWidth = 12;
        gl.marginHeight = 12;
        gl.horizontalSpacing = 4;
        gl.verticalSpacing = 4;
        gl.numColumns = 2;
        composite.setLayout(gl);
        txtlab1.setLayoutData(
            new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER | GridData.GRAB_HORIZONTAL));
        txt1.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER));

        ((GridData)txtlab1.getLayoutData()).widthHint = 100;
        ((GridData)txt1.getLayoutData()).widthHint = 200;

        composite.pack();
        setBlockOnOpen(true);

        return composite;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Import database");
    }

    protected void cancelPressed() {
        dbname = null;
        super.cancelPressed();
    }

    protected void okPressed() {
        dbname = txt1.getText();
        super.okPressed();
    }

}
