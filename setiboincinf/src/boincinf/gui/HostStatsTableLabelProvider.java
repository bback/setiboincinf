package boincinf.gui;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import boincinf.BoincNetStats;
import boincinf.netstat.HostStats;

public class HostStatsTableLabelProvider implements ITableLabelProvider {

    public String getColumnText(Object element, int column_index) {
        HostStats hs = (HostStats)element;
        return hs.getValueAt(column_index);
    }

    public void buildTableColumns(Table t) {
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
    
    public void saveStateToConfig(Table t) {
        TableColumn[] tc = t.getColumns();
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_0", ""+tc[0].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_1", ""+tc[1].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_hoststatstable_colwidth_2", ""+tc[2].getWidth());
    }

    public void addListener(ILabelProviderListener ilabelproviderlistener) {}

    public void dispose() {}

    public boolean isLabelProperty(Object obj, String s) {
        return false;
    }

    public void removeListener(ILabelProviderListener ilabelproviderlistener) {}

    public Image getColumnImage(Object element, int column_index) {
        if (column_index != 0) {
            return null;
        }
        return Util.getImageRegistry().get("run");    
    }
}
