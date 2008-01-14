package boincinf.netstat;

import java.sql.*;
import java.text.*;
import java.util.*;

public class HostStats {

	public ArrayList<SingleHostStat> singleHosts = new ArrayList<SingleHostStat>();
    public Timestamp timestamp = null;
	public double sum_avg_credit;
	public double sum_credit;

	public void sumSingleHosts() {
		sum_avg_credit = 0.0d;
		sum_credit = 0.0d;

		final Iterator<SingleHostStat> i = singleHosts.iterator();
		while(i.hasNext()) {
			final SingleHostStat shs = i.next();
			sum_avg_credit += shs.avg_credit;
			sum_credit += shs.credit;
		}
        // truncate to x.xx
        sum_credit = (Math.round((sum_credit*100.00d))) / 100.0d;
	}

    private static NumberFormat df = null;

    private static NumberFormat getFormat() {
        if( df == null ) {
            df = DecimalFormat.getInstance();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
        }
        return df;
    }

    public String getValueAt(final int column_index) {
        if (column_index == 0) {
            return timestamp.toString();
        }
        if (column_index == 1) {
            return getFormat().format(sum_credit);
        }
        if (column_index == 2) {
            return getFormat().format(sum_avg_credit);
        }
        return "*err*";
    }

//    public int compareTo( HostStats anOther, int column_index ) {
//        if (column_index == 0) {
//            return timestamp.toString();
//        }
//        if (column_index == 1) {
//            return getFormat().format(sum_credit);
//        }
//        if (column_index == 2) {
//            return getFormat().format(sum_avg_credit);
//        }
//        return 0;
//    }

	public void addSingleHost(final SingleHostStat sh) {
        singleHosts.add(sh);
    }

    @Override
    public String toString() {
        return timestamp.toString();
    }
}
