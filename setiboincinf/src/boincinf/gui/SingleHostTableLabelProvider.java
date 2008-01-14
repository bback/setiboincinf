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

import java.text.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.netstat.*;

public class SingleHostTableLabelProvider implements ITableLabelProvider {
    NumberFormat df_float;

    public SingleHostTableLabelProvider() {
        df_float = DecimalFormat.getInstance();
        df_float.setMaximumFractionDigits(2);
        df_float.setMinimumFractionDigits(2);
    }

    public String getColumnText(final Object element, final int column_index) {
        if (element == null) {
            return "err";
        }
        final SingleHostStat shs = (SingleHostStat)element;
        return shs.getValueAt(column_index);
    }

    public void buildTableColumns(final Table t) {
        TableColumn column;
        int w;
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("#");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_0");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(55);
        }
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("HostId");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_1");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(60);
        }
        column = new TableColumn(t, SWT.LEFT);
        column.setText("Host Name");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_2");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(200);
        }
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("Credits");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_3");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(80);
        }
        // NEW
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("dCredits");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_3b");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(80);
        }

        column = new TableColumn(t, SWT.RIGHT);
        column.setText("Avg.");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_4");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(80);
        }
        // NEW
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("dAvg.");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_4b");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(80);
        }

        column = new TableColumn(t, SWT.LEFT);
        column.setText("System");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_5");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(275);
        }
        column = new TableColumn(t, SWT.LEFT);
        column.setText("OS");
        w = BoincNetStats.getCfg().getIntProperty("guistate_singlehoststatstable_colwidth_6");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(310);
        }
    }

    public void saveStateToConfig(final Table t) {
        final TableColumn[] tc = t.getColumns();
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_0", ""+tc[0].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_1", ""+tc[1].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_2", ""+tc[2].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_3", ""+tc[3].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_4", ""+tc[4].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_5", ""+tc[5].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_6", ""+tc[6].getWidth());
    }

    public void addListener(final ILabelProviderListener ilabelproviderlistener) {}

    public void dispose() {}

    public boolean isLabelProperty(final Object obj, final String s) {
        return false;
    }

    public void removeListener(final ILabelProviderListener ilabelproviderlistener) {}

    public Image getColumnImage(final Object element, final int column_index) {
        if( column_index != 0 ) {
            return null;
        }
        if (BoincInfGui.showSpecialIcons) {
            return ((SingleHostStat)element).getSpecialIcon();
        }
        return null;
    }
}
