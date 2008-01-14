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
package boincinf;

import java.io.*;
import java.text.*;
import java.util.*;

import boincinf.gui.*;
import boincinf.netstat.*;
import boincinf.util.*;

public class BoincNetStats {
    public static String VERSION = "v0.42 (05/14/2005)";
    public static String TITLE = "SetiBoincInf - NetStats ";

    private static Config cfg = null;

    private static boolean isGuiMode = false;
//    private static BoincInfGui gui = null;

    private static PrintWriter output = null;

    public static void main(final String[] args) {
        System.out.println(TITLE + VERSION);

        output = new PrintWriter(System.out);
        out("Startup.");

        Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run () { onShutdown(); }
            });
        run(args);
    }

    private static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    public static void out(final String s) {
        final Date d = new Date();
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(dateFormat.format(d));
        sb.append("] ");
        sb.append(s);
        System.out.println(sb.toString());
//        output.println(sb.toString());
//        output.flush();
    }

    public static void onShutdown() {
        out("Shutdown of application requested.");
//        if( getGui() != null ) {
//            gui.onShutdown();
//        }
        out("Shutdown of application finished.");
        output.close();
    }

    public static Config getCfg() {
        if( cfg == null ) {
            cfg = new Config();
        }
        return cfg;
    }

    private static void run(final String[] args) {
        getCfg().readConfig();

        if (args.length == 0) {
            final String dbname_gui = getCfg().getStrProperty("dbname_gui");
            ensureDatabaseTables(dbname_gui);
            isGuiMode = true;
            runGui();
        } else if (args[0].equals("update")) {
            try {
                final String dbname_upd = getCfg().getStrProperty("dbname_update");
                ensureDatabaseTables(dbname_upd);
                runUpdate(dbname_upd);
            } catch (final Exception e) {
                out("Error, update was not successful: \n"+e.toString());
                doErrorExit();
                return;
            }
        } else if (args[0].equals("updateloop")) {
            int update_minutes = 60; // default
            if (args.length == 2) {
                try {
                    final int i = Integer.parseInt(args[1]);
                    update_minutes = i;
                } catch (final NumberFormatException e) {
                    out(
                        "Warning: Invalid updateloop minute value given ("
                            + args[1]
                            + "), assuming 60 minutes.");
                }
            }
            if( update_minutes < 30 ) {
                out("Enforced update interval of 30 minutes (minimum).");
                update_minutes = 30;
            }
            final String dbname_upd = getCfg().getStrProperty("dbname_update");
            ensureDatabaseTables(dbname_upd);
            updateLoop(update_minutes, dbname_upd);
        } else {
            out("Unknown command line parameter given: " + args[0]);
            doErrorExit();
            return;
        }
        out("Ready.");
        System.exit(0);
    }

    private static void ensureDatabaseTables(final String dbname)
    {
        try {
            final SQLStorage s = SQLStorage.createSQLStore(dbname);
            s.ensureDBTables();
            s.close();
        } catch (final Exception e1) {
            out("Error: Could not open database '"+dbname+"' to check table existence. Maybe another program accesses the database?");
            out(e1.toString());
            doErrorExit();
            return;
        }
    }

    private static boolean isGuiMode() {
        return isGuiMode;
    }
//    private static BoincInfGui getGui() {
//        return gui;
//    }

    private static void updateLoop(final int minutes, final String dbname) {
        out("Updating...");
        try {
            runUpdate(dbname);
        } catch (final Exception e) {
            out(e.getMessage());
        }
        final long updateRangeMs = minutes * 60 * 1000;
        out("Update loop, interval is " + minutes + " minutes.");
        while (true) {
            final long targettime = System.currentTimeMillis() + updateRangeMs;
            out("Sleeping for " + minutes + " minutes ...");
            while (System.currentTimeMillis() < targettime) {
                try {
                    Thread.sleep(targettime - System.currentTimeMillis());
                } catch (final InterruptedException ex) {
                    ;
                }
            }
            out("Updating...");
            try {
                runUpdate(dbname);
            } catch (final Exception e) {
                out(e.getMessage());
            }
        }
    }

    private static HostStats runUpdate(final String dbname) throws Exception {
        final SQLStorage st = SQLStorage.createSQLStore(dbname);
        if (st == null) {
            throw new Exception("Unable to open database '"+dbname+"'. Maybe another program accesses the database?");
        }
        HostStats hs = null;
        try {
            hs = runUpdate( st );
        } catch(final Exception ex) {
            st.close();
            throw new Exception("Update error: "+ex.toString());
        }
        st.close();
        return hs;
    }

    public static HostStats runUpdate(final SQLStorage store) throws Exception {
        final HostStats hs = retrieveHostStats();
        if (hs != null) {
            out("Inserting statistics data into SQL database ...");
            final int result = store.saveToSQL(hs);
            if( result == SQLStorage.SAVE_DUPLICATE_DATA ) {
                out("Ignored duplicate data.");
                return null;
            }
            if( result != SQLStorage.SAVE_OK ) {
                return null;
            }
        }
        return hs;
    }

    private static HostStats retrieveHostStats() throws Exception {
        final String accountID = cfg.getStrProperty("accountid");
        if (accountID.length() == 0) {
            throw new Exception("No account id configured.");
        }
        final BoincSetiHostsWebsite website = new BoincSetiHostsWebsite();
        final HostStats hs = website.getBoincSetiHostsWebsite(accountID);
        return hs;
    }

    private static void runGui() {
        try {
//            gui =
            new BoincInfGui();
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application if it runs in console mode.
     * In gui mode we do not exit but show dialogs.
     */
    private static void doErrorExit() {
        if (isGuiMode() == false) {
            System.out.println("\nPress ENTER to continue ...");
            try {
                System.in.read();
            } catch (final IOException e) {
                ;
            }
            System.exit(0);
        }
    }
}
