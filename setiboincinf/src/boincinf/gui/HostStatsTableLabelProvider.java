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

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import boincinf.*;
import boincinf.netstat.*;

public class HostStatsTableLabelProvider implements ITableLabelProvider {

    public String getColumnText(final Object element, final int column_index) {
        final HostStats hs = (HostStats)element;
        return hs.getValueAt(column_index);
    }

    public void buildTableColumns(final Table t) {
        TableColumn column = new TableColumn(t, SWT.LEFT);
        column.setText("Timestamp");
        int w;
        w = BoincNetStats.getCfg().getIntProperty("guistate_hoststatstable_colwidth_0");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(200);
        }
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("Credits");
        w = BoincNetStats.getCfg().getIntProperty("guistate_hoststatstable_colwidth_1");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(120);
        }
        column = new TableColumn(t, SWT.RIGHT);
        column.setText("Avg. Credits");
        w = BoincNetStats.getCfg().getIntProperty("guistate_hoststatstable_colwidth_2");
        if( w > 0 ) {
            column.setWidth(w);
        } else {
            column.setWidth(100);
        }
    }

    public void saveStateToConfig(final Table t) {
        final TableColumn[] tc = t.getColumns();
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_0", ""+tc[0].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_1", ""+tc[1].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_2", ""+tc[2].getWidth());
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
        return Util.getImageRegistry().get("run");
    }
}
