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
package boincinf.netstat;

import java.text.*;

import org.eclipse.swt.graphics.*;

import boincinf.*;

public class SingleHostStat {
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

	public SingleHostStat(
	        final int rank,
	        final String hid,
	        final String hname,
	        final String acred,
	        final String cred,
	        final String stype,
	        final String os)
	{
        gui_rank = rank;
		hostname = hname;
		systemtype = stype;
		ostype = os;
        hostid=0;
        try { hostid = Integer.parseInt(hid); }
        catch(final Exception ex) { BoincNetStats.out(ex.toString()); }

		avg_credit = credit = 0.0d;
		try { avg_credit = Double.parseDouble(acred); }
        catch(final Exception ex) { BoincNetStats.out(ex.toString()); }

		try { credit = Double.parseDouble(cred); }
        catch(final Exception ex) { BoincNetStats.out(ex.toString()); }
	}

    public void computeDeltas(final SingleHostStat previousShs) {
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
    public void setSpecialIcon(final Image i) {
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

    public String getValueAt(final int column_index) {
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
