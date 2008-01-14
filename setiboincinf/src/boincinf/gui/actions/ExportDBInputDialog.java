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

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ExportDBInputDialog extends org.eclipse.jface.dialogs.Dialog {
    Shell shell;
    Text txt1;
    String dbname;

    public ExportDBInputDialog(final Shell s) {
        super(s);
        shell = s;
    }

    public String getDatabaseName() {
        return dbname;
    }

    @Override
    protected Control createDialogArea(final Composite parent) {
        final Composite composite = (Composite)super.createDialogArea(parent);

        final Label txtlab1 = new Label(composite, SWT.NONE);
        txtlab1.setText("Database name");

        txt1 = new Text(composite, SWT.BORDER);
        txt1.setText("");

        final GridLayout gl = new GridLayout();
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

    @Override
    protected void configureShell(final Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Export database");
    }

    @Override
    protected void cancelPressed() {
        dbname = null;
        super.cancelPressed();
    }

    @Override
    protected void okPressed() {
        dbname = txt1.getText();
        super.okPressed();
    }
}
