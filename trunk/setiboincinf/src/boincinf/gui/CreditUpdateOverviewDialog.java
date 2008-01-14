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
package boincinf.gui;

import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.netstat.*;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class CreditUpdateOverviewDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button Bclose;
	private TableViewer tableViewer1;
	private Label label1;

    private final SQLStorage sqlStore;
    private final BoincInfGui gui;

    public CreditUpdateOverviewDialog(final BoincInfGui g, final SQLStorage store) {
        super(g.getShell());
        gui = g;
        sqlStore = store;
    }

	public void open() {
		try {
			final Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);

			final GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.numColumns = 3;
            {
                label1 = new Label(dialogShell, SWT.NONE);
                final GridData label1LData = new GridData();
                label1LData.horizontalSpan = 3;
                label1LData.grabExcessHorizontalSpace = true;
                label1.setLayoutData(label1LData);
                label1.setText("Number of days since last increase of credits:");
            }
            {
                final GridData tableViewer1LData = new GridData();
                tableViewer1LData.horizontalSpan = 3;
                tableViewer1LData.grabExcessHorizontalSpace = true;
                tableViewer1LData.horizontalAlignment = GridData.FILL;
                tableViewer1LData.grabExcessVerticalSpace = true;
                tableViewer1LData.verticalAlignment = GridData.FILL;
                tableViewer1 = new TableViewer(dialogShell, SWT.BORDER | SWT.FULL_SELECTION);
                tableViewer1.getControl().setLayoutData(tableViewer1LData);
            }
            {
                Bclose = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                final GridData button1LData = new GridData();
                button1LData.grabExcessHorizontalSpace = true;
                button1LData.horizontalAlignment = GridData.END;
                button1LData.horizontalSpan = 3;
                Bclose.setLayoutData(button1LData);
                Bclose.setText("Close");
                Bclose.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(final SelectionEvent evt) {
                        BcloseWidgetSelected(evt);
                    }
                });
            }
            dialogShell.setText("Credit update overview");
            postInitGui();
			dialogShell.layout();
            dialogShell.addShellListener(new ShellAdapter() {
                @Override
                public void shellClosed(final ShellEvent evt) {
                    rootShellClosed(evt);
                }
            });
			dialogShell.open();
			final Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch()) {
                    display.sleep();
                }
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

    protected void postInitGui() {

        tableViewer1.setContentProvider(new CreditUpdateTableContentProvider());
        final CreditUpdateTableLabelProvider lp = new CreditUpdateTableLabelProvider();
        tableViewer1.setLabelProvider(lp);
        lp.buildTableColumns(tableViewer1.getTable());
        tableViewer1.getTable().setHeaderVisible(true);
        tableViewer1.getTable().setLinesVisible(true);

        final List<CreditUpdateData> datas = sqlStore.getCreditUpdateOverviewData();
        tableViewer1.setInput(datas);

        restoreStateFromConfig();
    }

    class CreditUpdateTableLabelProvider implements ITableLabelProvider {

        public String getColumnText(final Object element, final int column_index) {
            final CreditUpdateData hs = (CreditUpdateData)element;
            return hs.getValueAt(column_index);
        }

        public void buildTableColumns(final Table t) {
            int w;

            TableColumn column = new TableColumn(t, SWT.LEFT);
            column.setText("HostId");
            w = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewtable_colwidth_0");
            if( w > 0 ) {
                column.setWidth(w);
            } else {
                column.setWidth(65);
            }

            column = new TableColumn(t, SWT.LEFT);
            column.setText("Hostname");
            w = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewtable_colwidth_1");
            if( w > 0 ) {
                column.setWidth(w);
            } else {
                column.setWidth(230);
            }

            column = new TableColumn(t, SWT.RIGHT);
            column.setText("Days");
            w = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewtable_colwidth_2");
            if( w > 0 ) {
                column.setWidth(w);
            } else {
                column.setWidth(65);
            }
        }

        public void saveStateToConfig(final Table t) {
            final TableColumn[] tc = t.getColumns();
            BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewtable_colwidth_0", ""+tc[0].getWidth());
            BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewtable_colwidth_1", ""+tc[1].getWidth());
            BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewtable_colwidth_2", ""+tc[2].getWidth());
        }

        public void addListener(final ILabelProviderListener ilabelproviderlistener) {}

        public void dispose() {}

        public boolean isLabelProperty(final Object obj, final String s) {
            return false;
        }

        public void removeListener(final ILabelProviderListener ilabelproviderlistener) {}

        public Image getColumnImage(final Object element, final int column_index) {
            if (column_index != 0) {
                return null;
            }
            return null;
            //return Util.getImageRegistry().get("run");
        }
    }


    class CreditUpdateTableContentProvider implements IStructuredContentProvider {
        public Object[] getElements(final Object element) {
            // called after setInput
            final List v = (List)element;
            return v.toArray();
        }

        public void dispose() {}

        public void inputChanged(final Viewer viewer, final Object old_object, final Object new_object) {}
    }

	private void BcloseWidgetSelected(final SelectionEvent evt) {
        saveStateToConfig();
        dialogShell.close();
        dialogShell.dispose();
	}

    private void rootShellClosed(final ShellEvent evt) {
        saveStateToConfig();
    }

    public void saveStateToConfig() {
        final Point size = dialogShell.getSize();
        BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewdialog_size_x", ""+size.x);
        BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewdialog_size_y", ""+size.y);

        final Point loc = dialogShell.getLocation();
        BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewdialog_loc_x", ""+loc.x);
        BoincNetStats.getCfg().setProperty("guistate_creditupdateoverviewdialog_loc_y", ""+loc.y);

        ((CreditUpdateTableLabelProvider)tableViewer1.getLabelProvider()).saveStateToConfig(tableViewer1.getTable());
    }

    public void restoreStateFromConfig() {
        final int size_x = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewdialog_size_x");
        final int size_y = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewdialog_size_y");
        if( size_x > 0 && size_y > 0 ) {
            dialogShell.setSize(size_x, size_y);
        } else {
            dialogShell.setSize(450, 430);
        }
        int loc_x = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewdialog_loc_x");
        int loc_y = BoincNetStats.getCfg().getIntProperty("guistate_creditupdateoverviewdialog_loc_y");
        if( loc_x > 0 && loc_y > 0 ) {
            dialogShell.setLocation(loc_x, loc_y);
        } else {
            // center on parent
            loc_x = gui.getShell().getLocation().x + 25;
            loc_y = gui.getShell().getLocation().y + 25;
            dialogShell.setLocation(loc_x, loc_y);
        }
    }

}
