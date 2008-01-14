package boincinf.netstat;

import java.text.*;

import org.eclipse.swt.graphics.*;

import boincinf.*;

public class SingleHostStat
{
	public long hostid;
    public String hostname;
    public double avg_credit;
    public double credit;
    public double d_avg_credit;
    public double d_credit;
    public String systemtype;
    public String ostype;
    
    public int gui_rank = 0;
    
    public Image specialIcon = null;
    
    public SingleHostStat() {}
    
	public SingleHostStat(int rank, String hid, String hname, String acred, String cred, String stype, String os)
	{
        gui_rank = rank;
		hostname = hname;
		systemtype = stype;
		ostype = os;
        hostid=0;
        try { hostid = Integer.parseInt(hid); } 
        catch(Exception ex) { BoincNetStats.out(ex.toString()); }
		
		avg_credit = credit = 0.0d;
		try { avg_credit = Double.parseDouble(acred); } 
        catch(Exception ex) { BoincNetStats.out(ex.toString()); }
        
		try { credit = Double.parseDouble(cred); } 
        catch(Exception ex) { BoincNetStats.out(ex.toString()); }
	}
    
    public void computeDeltas(SingleHostStat previousShs) {
        if( previousShs == null ) {
            d_avg_credit = 0.0;
            d_credit = 0.0;
        } else {
            d_avg_credit = avg_credit - previousShs.avg_credit;
            d_credit = credit - previousShs.credit;
        }
    }
    
    /**
     * - beim obersten update (HostStats) eine spezielle singlehoststable mit icons:
     *   - grün = credits seit letztem mal gestiegen
     *   - dunkleres grün = credits heute schon mal angestiegen
     *   - leichtes rot = heute noch nicht angestiegen
     * 
     * Method wird nur gerufen wenn es oberste HostStat ist und tablecolumn == 0 ! 
     */
    public Image getSpecialIcon() {
        return specialIcon;
    }
    public void setSpecialIcon(Image i) {
        specialIcon = i;
    }
    
    private static NumberFormat df_float = null;

    private static NumberFormat getFloatFormat() {
        if( df_float == null ) {
            df_float = DecimalFormat.getInstance();
            df_float.setMaximumFractionDigits(2);
            df_float.setMinimumFractionDigits(2);
        }
        return df_float;
    }
    
    public String getValueAt(int column_index) {
        // place, hostid, hostname, cred, avgcred, sys, os
        if (column_index == 0) {
            return " " + gui_rank; /* df_int.format(shs.gui_rank); */
        }
        if (column_index == 1) {
            return "" + hostid;
        }
        if (column_index == 2) {
            return hostname;
        }
        if (column_index == 3) {
            return getFloatFormat().format(credit);
        }
        
        // NEW
        if (column_index == 4) {
            return getFloatFormat().format(d_credit);
        }

        if (column_index == 5) {
            return getFloatFormat().format(avg_credit);
        }

        // NEW
        if (column_index == 6) {
            return getFloatFormat().format(d_avg_credit);
        }

        if (column_index == 7) {
            return systemtype;
        }
        if (column_index == 8) {
            return ostype;
        }
        return "";
    }
    
//    public int compareTo( SingleHostStat anOther, int column_index ) {
//        // place, hostid, hostname, cred, avgcred, sys, os
//        if (column_index == 0) {
//            return " " + gui_rank; /* df_int.format(shs.gui_rank); */
//        }
//        if (column_index == 1) {
//            return "" + hostid;
//        }
//        if (column_index == 2) {
//            return hostname;
//        }
//        if (column_index == 3) {
//            return getFloatFormat().format(credit);
//        }
//        if (column_index == 4) {
//            return getFloatFormat().format(avg_credit);
//        }
//        if (column_index == 5) {
//            return systemtype;
//        }
//        if (column_index == 6) {
//            return ostype;
//        }
//        return "";
//    }
    
}