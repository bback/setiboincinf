package boincinf.gui;

import java.text.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import boincinf.BoincNetStats;
import boincinf.netstat.SingleHostStat;

public class SingleHostTableLabelProvider implements ITableLabelProvider {
    NumberFormat df_float;

    public SingleHostTableLabelProvider() {
        df_float = DecimalFormat.getInstance();
        df_float.setMaximumFractionDigits(2);
        df_float.setMinimumFractionDigits(2);
    }

    public String getColumnText(Object element, int column_index) {
        if (element == null)
            return "err";
        SingleHostStat shs = (SingleHostStat)element;
        return shs.getValueAt(column_index);
    }

    public void buildTableColumns(Table t) {
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
    
    public void saveStateToConfig(Table t) {
        TableColumn[] tc = t.getColumns();
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_0", ""+tc[0].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_1", ""+tc[1].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_2", ""+tc[2].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_3", ""+tc[3].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_4", ""+tc[4].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_5", ""+tc[5].getWidth());
        BoincNetStats.getCfg().setProperty("guistate_singlehoststatstable_colwidth_6", ""+tc[6].getWidth());
    }

    public void addListener(ILabelProviderListener ilabelproviderlistener) {}

    public void dispose() {}

    public boolean isLabelProperty(Object obj, String s) {
        return false;
    }

    public void removeListener(ILabelProviderListener ilabelproviderlistener) {}

    public Image getColumnImage(Object element, int column_index) {
        if( column_index != 0 ) {
            return null;
        }
        if (BoincInfGui.showSpecialIcons) {
            return ((SingleHostStat)element).getSpecialIcon();
        }
        return null;
    }
}
