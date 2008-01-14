package boincinf.netstat;

import java.sql.*;
import java.util.*;

import org.eclipse.swt.graphics.*;

import boincinf.*;
import boincinf.gui.*;

public class SQLStorage
{
    private static String JDBC_URL = "jdbc:hsqldb:";

    Connection conn;
    PreparedStatement deleteSingleHost = null;
    PreparedStatement deleteHostStat = null;
    PreparedStatement listSingleHosts = null;
    PreparedStatement listHostStats = null;
    PreparedStatement creditsSingleHosts = null;

    public SQLStorage(final String jdbcUrl) throws SQLException
    {
        conn = DriverManager.getConnection(jdbcUrl, "sa", "");
        // prepare for read/delete in db
        try {
            deleteHostStat = conn.prepareStatement("DELETE FROM HostStats WHERE when_taken=?");
            deleteSingleHost = conn.prepareStatement("DELETE FROM SingleHosts WHERE when_taken=?");
            listSingleHosts = conn.prepareCall("SELECT when_taken,host_id,credit,avg_credit,host_name,system_type,os_type FROM SingleHosts WHERE when_taken=? ORDER BY credit DESC");
            listHostStats = conn.prepareCall("SELECT when_taken,sum_credit,sum_avg_credit FROM HostStats ORDER BY when_taken DESC");
            creditsSingleHosts = conn.prepareCall("SELECT when_taken,sum_credit,sum_avg_credit FROM HostStats WHERE when_taken>? ORDER BY when_taken ASC");
        } catch(final Exception e) {
            BoincNetStats.out("PrepareCall: "+e.toString());
        }
    }

    /**
     * Builds a List of CreditUpdateData items.
     * Computes the daysSinceLastCreditUpdate for each of the hosts
     * that exist in latest update.
     */
    public List<CreditUpdateData> getCreditUpdateOverviewData() {

        final ArrayList<CreditUpdateData> result = new ArrayList<CreditUpdateData>();

        ResultSet rs = null;
        Statement s = null;

        try {
            Timestamp maxWhenTaken;

            s = getConnection().createStatement();

            rs = s.executeQuery("SELECT MAX(when_taken) FROM SingleHosts");
            if( rs.next() ) {
                maxWhenTaken = rs.getTimestamp(1);
                rs.close();
            } else {
                System.out.println("Error: Could not retrieve maximum when_taken.");
                rs.close();
                s.close();
                return null;
            }
//            String sqlStr = "SELECT when_taken,host_id,credit FROM SingleHosts WHERE host_id = ? AND credit < ? ORDER BY credit DESC";
            final String sqlStr = "SELECT when_taken FROM SingleHosts WHERE host_id = ? AND credit < ? ORDER BY credit DESC";
            final PreparedStatement ps = getConnection().prepareStatement(sqlStr);

            final ArrayList<SingleHostStat> singleHosts = retrieveSingleHosts(maxWhenTaken);
            for( final SingleHostStat singleHostStat : singleHosts ) {
                final SingleHostStat shs = singleHostStat;
                final long actHostid = shs.hostid;
                final double actCredits = shs.credit;
                if( actHostid == 0 ) {
                    continue; // skip deleted hosts
                }
                // find Timestamp when the creditvalue was lower for this host
                ps.setLong(1, actHostid);
                ps.setDouble(2, actCredits);
                rs = ps.executeQuery();
                if( rs.next() ) {
                    final Timestamp smallerWhenTaken = rs.getTimestamp(1);

                    final long dateDiff = maxWhenTaken.getTime() - smallerWhenTaken.getTime();
                    // dateDiff is in ms
                    final long days = dateDiff / 1000 / 60 / 60 / 24;

                    final CreditUpdateData da = new CreditUpdateData();
                    da.hostId = shs.hostid;
                    da.hostName = shs.hostname;
                    da.daysSinceLastUpdate = (int)days;
                    result.add(da);
                } else {
                    // compute days since last update (MIN when_taken)
                    final ResultSet rs2 = s.executeQuery("SELECT MIN(when_taken) FROM SingleHosts WHERE host_id = "+actHostid);
                    if( rs2.next() ) {
                        final Timestamp smallerWhenTaken = rs2.getTimestamp(1);
                        final long dateDiff = maxWhenTaken.getTime() - smallerWhenTaken.getTime();
                        // dateDiff is in ms
                        final long days = dateDiff / 1000 / 60 / 60 / 24;

                        final CreditUpdateData da = new CreditUpdateData();
                        da.hostId = shs.hostid;
                        da.hostName = shs.hostname;
                        da.daysSinceLastUpdate = (int)days;
                        result.add(da);
                    } else {
                        System.out.println("Problem retrieving day diff.");
                    }
                    rs2.close();
                }
            }
        } catch(final Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if( rs != null ) {
                try {
                    rs.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if( s != null ) {
                try {
                    s.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // sort items in List by: daysSince, hostId
        Collections.sort(result, new CreditUpdateComparator());
        return result;
    }

    class CreditUpdateComparator implements Comparator<CreditUpdateData> {

        public int compare(final CreditUpdateData d1, final CreditUpdateData d2) {

            if( d1.daysSinceLastUpdate < d2.daysSinceLastUpdate ) {
                return 1;
            } else if( d1.daysSinceLastUpdate > d2.daysSinceLastUpdate ) {
                return -1;
            } else {
                if( d1.hostId < d2.hostId ) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    public int deleteHostStat(final HostStats hs)
    {
        final Timestamp ts = hs.timestamp;
        int del=0;
        try {
            deleteHostStat.clearParameters();
            deleteHostStat.setTimestamp(1, ts);
            del += deleteHostStat.executeUpdate();

            deleteSingleHost.clearParameters();
            deleteSingleHost.setTimestamp(1, ts);
            del += deleteSingleHost.executeUpdate();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    public void close()
    {
        if( deleteSingleHost != null ) {
            try { deleteSingleHost.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
        if( deleteHostStat != null ) {
            try { deleteHostStat.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
        if( listSingleHosts != null ) {
            try { listSingleHosts.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
        if( listHostStats != null ) {
            try { listHostStats.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
        if( creditsSingleHosts != null ) {
            try { creditsSingleHosts.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
        if( conn != null ) {
            try { conn.close(); } catch (final SQLException e1) { e1.printStackTrace(); }
        }
    }

    public ArrayList<HostStats> getHostStats()
    {
        ResultSet r = null;
        final ArrayList<HostStats> l = new ArrayList<HostStats>();
        try {
            r = listHostStats.executeQuery();
			while(r.next())
			{
				final HostStats hs = new HostStats();
				hs.singleHosts = null;
				hs.timestamp = r.getTimestamp(1);
				hs.sum_credit = r.getDouble(2);
                hs.sum_avg_credit = r.getDouble(3);
				l.add( hs );
			}
			r.close();
        }
        catch (final Throwable e) {
            //e.printStackTrace();
        }
        return l;
    }

    // we import only the when_taken column, sum_... are computed later
    public ArrayList<HostStats> importHostStats() {
        ResultSet r = null;
        final ArrayList<HostStats> l = new ArrayList<HostStats>();
        try {
            final PreparedStatement get = conn.prepareCall("SELECT when_taken FROM HostStats ORDER BY when_taken DESC");
            r = get.executeQuery();
            while(r.next())
            {
                final HostStats hs = new HostStats();
                hs.singleHosts = null;
                hs.timestamp = r.getTimestamp(1);
                l.add( hs );
            }
            r.close();
        }
        catch (final Throwable e) {
            //e.printStackTrace();
        }
        return l;
    }

    public Averages getAverages() {

        final Averages avgs = new Averages();

        final List<CreditsAtTime> list_all = getCreditAtTimeForDayCount(-1); // alle holen
        final List<CreditsAtTime> list_14 = new ArrayList<CreditsAtTime>();
        final List<CreditsAtTime> list_30 = new ArrayList<CreditsAtTime>();
        final long starttime_14 = getStartTime(14);
        final long starttime_30 = getStartTime(30);

        for( final CreditsAtTime creditsAtTime : list_all ) {
            final CreditsAtTime cat = creditsAtTime;
            final long t = cat.timestamp.getTime();
            if( t > starttime_14 ) {
                list_14.add(cat);
            }
            if( t > starttime_30 ) {
                list_30.add(cat);
            }
        }

        /**
         * Tagesdurchschnitt um den der Wert "Credits" anstieg.
         */
        avgs.avg14 = getCreditAverage(list_14, false);
        avgs.avg30 = getCreditAverage(list_30, false);
        avgs.avgAll = getCreditAverage(list_all, false);

        /**
         * Tagesdurchschnitt um den der Wert "Avg. Credits" anstieg.
         */
        avgs.avg14_grow = getCreditAverage(list_14, true);
        avgs.avg30_grow = getCreditAverage(list_30, true);
        avgs.avgAll_grow = getCreditAverage(list_all, true);

        return avgs;
    }

    private long getStartTime(final int daycount) {
        // TODO: we start by NOW - daycount, maybe we should ignore the time and select only by date
        // (by setting startts to begin of day)
        if( daycount > 0 ) {
            final long now = System.currentTimeMillis();
            final long diff = (daycount * 24l * 60l * 60l * 1000l);
            final long starttime = now - diff;
            return starttime;
        } else {
            return 0;
        }
    }

    public List<CreditsAtTime> getCreditAtTimeForDayCount(final int daycount) {

        final ArrayList<CreditsAtTime> list = new ArrayList<CreditsAtTime>();
        try {
            final java.sql.Timestamp startts = new java.sql.Timestamp( getStartTime(daycount) );
            creditsSingleHosts.clearParameters();
            creditsSingleHosts.setTimestamp(1, startts);
            final ResultSet r = creditsSingleHosts.executeQuery();
            while(r.next()) {
                final CreditsAtTime cat = new CreditsAtTime();
                cat.timestamp = r.getTimestamp(1);
                cat.sum_credits = r.getDouble(2);
                cat.sum_avg_credits = r.getDouble(3);
                list.add(cat);
            }
            r.close();
        } catch(final Throwable t) {
            t.printStackTrace();
        }
        return list;
    }

    /**
     * Returns the average increase of credits per day,
     * average is computed using the last daycount days.
     *
     * Wird mittels Linearer Regression berechnet.
     *
     * Deleted host are not included in the computation.
     */
    private String getCreditAverage(final List<CreditsAtTime> list, final boolean use_avg_credit)
    {
        final String DEFAULT_RETVAL = "0.00";

        if( list.size() < 2 ) {
            return DEFAULT_RETVAL;
        }

		try {
            double result;

            if( use_avg_credit ) {
    			long count = 0;
    			double sum_x = 0;
    			double sum_x2 = 0;
    			double sum_y = 0.0;
    			double sum_xy = 0.0;
    			double beforeCredit = 0.0;

    			for( final CreditsAtTime creditsAtTime : list ) {

                    final CreditsAtTime cat = creditsAtTime;
    				// time is X, credit is Y
    				final long times = (cat.timestamp.getTime() / 1000L / 60L); // ms -> minutes
    				final double credit = cat.sum_avg_credits;
                    // TEST: doppelte credit messwerte entfernen, verziehen in linearer regression das ergebnis
                    if( credit == beforeCredit ) {
                        continue;
                    } else {
                        beforeCredit = credit;
                    }
                    count++;
    				sum_x += times;
    				sum_y += credit;
    				sum_x2 += Math.pow(times,2);
    				sum_xy += times * credit;
    			}
    			// compute
    			result = (count * sum_xy - sum_x*sum_y) /
       			         (count * sum_x2 - Math.pow(sum_x, 2));
    			result *= (24*60); // min -> day

            } else {

                final CreditsAtTime first = list.get(0);
                final CreditsAtTime last = list.get(list.size()-1);

                double t = last.timestamp.getTime() - first.timestamp.getTime(); // delta tage
                t = t / 1000 / 60 / 60 / 24;

                result = (last.sum_credits - first.sum_credits) / t;
            }

            result = ( ((long)(result*100)) / 100.0 ); // 'format' to 0.00

            // build output: x.xx
            String res = ""+result;
            int dec_pos; // pos of dec. point counted from right, must be 2
            dec_pos = res.indexOf(".");
            if( dec_pos < 0 ) {
                res += ".00";
            } else {
                dec_pos = res.length()-1 - dec_pos;
                if( dec_pos == 1 ) {
                    res += "0";
                }
            }

            return res;
        } catch (final Throwable e) {
			e.printStackTrace();
		}
    	return "*err*";
    }
/*
function regress(f) {
  var sum_x = 0, sum_x2 = 0
  var sum_y = 0, sum_y2 = 0
  var sum_xy = 0
  var hilf = 0
  for (i = 1; i <= anzahl; i++) {
    sum_x = sum_x + x[i]*1
    sum_y = sum_y + y[i]*1
    sum_x2 = sum_x2 + x[i]*x[i]
    sum_y2 = sum_y2 + y[i]*y[i]
    sum_xy = sum_xy + x[i]*y[i]
  }
  hilf = (sum_xy-sum_x*sum_y/anzahl)/(sum_x2-sum_x*sum_x/anzahl)
  f.k.value = Math.round(hilf*1000)/1000   == anstieg m
  hilf = (sum_y-f.k.value*sum_x)/anzahl
  f.d.value = Math.round(hilf*1000)/1000   == Y Wert z   ( y = m*x+z )
  hilf = (sum_xy-sum_x*sum_y/anzahl)/Math.sqrt((sum_x2-sum_x*sum_x/anzahl)*(sum_y2-sum_y*sum_y/anzahl))
  f.r.value = Math.round(hilf*1000)/1000   == Rxy Regressionskoeffizient
} */

    public boolean existRecordsInRange( final Timestamp low, final Timestamp up)
    {
        final String sql = "SELECT when_taken FROM HostStats WHERE when_taken > ? AND when_taken < ?";
        try {
            final PreparedStatement ps = getConnection().prepareCall(sql);
            ps.setTimestamp(1, low);
            ps.setTimestamp(2, up);
            final ResultSet rs = ps.executeQuery();
            boolean result;
            if( rs.next() == true ) {
                result = true;
            } else {
                result = false;
            }
            rs.close();
            ps.close();
            return result;
        } catch(final Exception ex) {}
        return true;
    }

    public ArrayList<SingleHostStat> retrieveSingleHosts(final Timestamp ts)
    {
        final ArrayList<SingleHostStat> l = new ArrayList<SingleHostStat>();
        ResultSet r = null;
        int cnt=1;
        try {
            SingleHostStat deleted_hosts_stat = null;
//            SingleHostStat previous_hosts_stat = null;
            listSingleHosts.setTimestamp(1, ts);
            r = listSingleHosts.executeQuery();
			while(r.next()) {
				final SingleHostStat shs = new SingleHostStat();
                shs.gui_rank = cnt;
				shs.hostid = r.getInt(2);
                shs.credit = r.getDouble(3); // TODO: generally use non-float
				shs.avg_credit = r.getDouble(4);
				shs.hostname = r.getString(5);
				shs.systemtype = r.getString(6);
				shs.ostype = r.getString(7);

				// FIXME: compute deltas from previous stats entry

                shs.computeDeltas(null);

//                shs.computeDeltas(previous_hosts_stat);
//
//                previous_hosts_stat = shs;

                // TODO: set special icon if requested
                if( BoincInfGui.showSpecialIcons ) {
                    Image icon = null;
                    /**
                     * - beim obersten update (HostStats) eine spezielle singlehoststable mit icons:
                     *   - grün = credits seit letztem mal gestiegen
                     *   - dunkleres grün = credits heute schon mal angestiegen
                     *   - leichtes rot = heute noch nicht angestiegen
                     */
                    icon = Util.getImageRegistry().get("test");

                    shs.setSpecialIcon(icon);
                }

                if( shs.hostid == 0 ) {
                    // sort deleted hosts on end of list
                    deleted_hosts_stat = shs;
                } else {
                    l.add( shs );
                    cnt++;
                }
			}
			r.close();
            if( deleted_hosts_stat != null ) {
                deleted_hosts_stat.gui_rank = l.size() + 1;
                l.add(deleted_hosts_stat);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return l;
    }

    public final static int SAVE_OK             = 0;
    public final static int SAVE_DUPLICATE_KEY  = 1;
    public final static int SAVE_DUPLICATE_DATA = 2;
    public final static int SAVE_ERROR          = 99;

    public int saveToSQL(final HostStats hs)
    {
        if( BoincNetStats.getCfg().getBooleanProperty("update_dont_store_unchanged_data") ) {
            // check if this hs.sum_credit value is already in database
            try {
                final PreparedStatement ins1 = conn.prepareCall("SELECT sum_credit FROM HostStats WHERE sum_credit=?");
                ins1.setDouble(1, hs.sum_credit);
                final ResultSet rs = ins1.executeQuery();
                boolean alreadyInDB = false;
                if( rs.next() ) {
                    alreadyInDB = true;
                }
                rs.close();
                ins1.close();
                if( alreadyInDB ) {
                    return SAVE_DUPLICATE_DATA;
                }
            } catch(final Exception ex) {
                ex.printStackTrace();
                return SAVE_ERROR;
            }
        }
        try {
            final PreparedStatement ins1 =
                conn.prepareCall("INSERT INTO HostStats (when_taken,sum_credit, sum_avg_credit) VALUES (?,?,?)");
            final PreparedStatement ins2 =
                conn.prepareCall("INSERT INTO SingleHosts (when_taken,host_id,credit,avg_credit,host_name,system_type,os_type) VALUES (?,?,?,?,?,?,?)");

            ins1.setTimestamp(1, hs.timestamp);
            ins1.setDouble(2, hs.sum_credit);
            ins1.setDouble(3, hs.sum_avg_credit);
            try {
                ins1.executeUpdate();
            } catch(final SQLException ex) {
                if( ex.getMessage().indexOf("Violation of unique index") > -1 ) {
                    // do nothing, just ignore double entries
//                    BoincNetStats.out("Duplicate record ignored on insert.");
                    return SAVE_DUPLICATE_KEY;
                }
                else {
                    ex.printStackTrace();
                    return SAVE_ERROR;
                }
            }

            final Iterator<SingleHostStat> i = hs.singleHosts.iterator();
            while(i.hasNext()) {
                final SingleHostStat shs = i.next();
                ins2.clearParameters();
                ins2.setTimestamp(1, hs.timestamp);
                ins2.setLong(2, shs.hostid);
                ins2.setDouble(3, shs.credit); // TODO: generally use non-float
                ins2.setDouble(4, shs.avg_credit);
                ins2.setString(5, shs.hostname);
                ins2.setString(6, shs.systemtype);
                ins2.setString(7, shs.ostype);
                ins2.executeUpdate();
            }
            ins1.close();
            ins2.close();
        } catch (final Throwable e) {
            e.printStackTrace();
            return SAVE_ERROR;
        }
        return SAVE_OK;
    }

    public void createDbTables() throws Exception
    {
        BoincNetStats.out("Creating SQL tables ...");

        final Statement stat = conn.createStatement();
        String strstmt = "CREATE TABLE HostStats (" +
            "when_taken TIMESTAMP PRIMARY KEY," +
            "sum_credit DOUBLE," +
            "sum_avg_credit DOUBLE," +
            "UNIQUE(when_taken)" +
            ")";
        stat.execute(strstmt);

        strstmt = "CREATE TABLE SingleHosts (" +
            "when_taken TIMESTAMP," +
            "host_id BIGINT," +
            "credit DOUBLE," +
            "avg_credit DOUBLE," +
            "host_name VARCHAR," +
            "system_type VARCHAR," +
            "os_type VARCHAR" +
            ")";
        stat.execute(strstmt);

        stat.execute("CREATE INDEX sh_ix ON SingleHosts (when_taken)");

        stat.close();
    }

    public Connection getConnection()
    {
        return conn;
    }

    public void ensureDBTables() throws Exception
    {
        if( doesTableExist("HostStats", "when_taken") == false ) {
            createDbTables();
        }
    }

    private boolean doesTableExist(final String tablename, final String tablefield)
    {
        final String sql = "SELECT "+tablefield+" FROM "+tablename;
        try {
            final Statement st = getConnection().createStatement();
            st.setMaxRows(1);
            st.executeQuery(sql);
        } catch(final Exception ex) {
            return false;
        }
        return true;
    }

    public static SQLStorage createSQLStore(final String dbname) throws Exception {
        final String jdbcurl = JDBC_URL + dbname;
        return new SQLStorage(jdbcurl);
    }

    static {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        }
        catch (final ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public class CreditsAtTime {
        public double sum_credits;
        public double sum_avg_credits;
        public java.sql.Timestamp timestamp;
    }
    public class Averages {
        public String avg14 = "";
        public String avg14_grow = "";
        public String avg30 = "";
        public String avg30_grow = "";
        public String avgAll = "";
        public String avgAll_grow = "";
    }
}
