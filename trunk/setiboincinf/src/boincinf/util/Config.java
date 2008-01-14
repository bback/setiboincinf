package boincinf.util;

import java.io.*;
import java.util.Properties;

import boincinf.BoincNetStats;

public class Config {
    private static final String CFG_FILENAME = "boincinf.cfg";
    private Properties props = null;


//    TreeMap sortedSettings = new TreeMap( settingsHash ); // sort the lines


    public void readConfig() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(CFG_FILENAME));
        } catch (Exception ex) {}
        props = p;
        
        ensureDefaults();
        
        if( saveConfig() == false ) {
            BoincNetStats.out("readConfig: Warning, could not save the configuration.");
        }
    }
    
    public void setProxiesFromConfig() {
        if( getBooleanProperty("connection_use_proxy") ) {
            System.setProperty("proxySet", "true");
            System.setProperty("proxyHost", getStrProperty("connection_proxy_server"));
            System.setProperty("proxyPort", ""+getIntProperty("connection_proxy_port"));
            System.setProperty("socksProxySet", "false");
        } else if( getBooleanProperty("connection_use_socks") ) {
            System.setProperty("socksProxySet", "true");
            System.setProperty("socksProxyHost", getStrProperty("connection_socks_server"));
            System.setProperty("socksProxyPort", ""+getIntProperty("connection_socks_port"));
            System.setProperty("proxySet", "false");
        } 
//        else {
//            System.setProperty("proxySet", "false");
//            System.setProperty("socksProxySet", "false");
//        }
    }
    
    // ensure values, set defaults
    private void ensureDefaults() {
        // database table names
        maintainDefaultString("dbname_gui", "boincinfdb_gui");
        maintainDefaultString("dbname_update", "boincinfdb_update");
        // update
        maintainDefaultBoolean("update_dont_store_unchanged_data", false);
        // auto DB update
        maintainDefaultBoolean("auto_db_update_enabled", false);
        maintainDefaultInt("auto_db_update_minutes", 5);
        maintainDefaultBoolean("auto_db_update_delete_updatedb_entries", true);
        // auto WEB update
        maintainDefaultBoolean("auto_web_update_enabled", false);
        maintainDefaultInt("auto_web_update_minutes", 120);
        // connection props
        maintainDefaultBoolean("connection_use_proxy", false);
        maintainDefaultString("connection_proxy_server", "");
        maintainDefaultInt("connection_proxy_port", 80);
        maintainDefaultBoolean("connection_use_socks", false);
        maintainDefaultString("connection_socks_server", "");
        maintainDefaultInt("connection_socks_port", 1080);
    }
    
    private void maintainDefaultString(String key, String defaultval) {
        if( props.get(key) == null ) {
            props.put(key, defaultval);
        }
    }
    private void maintainDefaultInt(String key, int defaultval) {
        if( props.get(key) == null ) {
            props.put(key, ""+defaultval);
        } else {
            int i;
            try {
                i = Integer.parseInt((String)props.get(key));
            } catch(Exception ex) {
                i = defaultval;
            }
            props.put(key, ""+i);
        }
    }
    private void maintainDefaultBoolean(String key, boolean defaultval) {
        if( props.get(key) == null ) {
            props.put(key, ""+defaultval);
        } else {
            boolean b;
            try {
                b = new Boolean((String)props.get(key)).booleanValue();
            } catch(Exception ex) {
                b = defaultval;
            }
            props.put(key, ""+b);
        }
    }
    
    public boolean saveConfig() {
        try {
            props.store(new FileOutputStream(CFG_FILENAME), "SetiBoincInf config file");
        } catch (Exception ex) {
            BoincNetStats.out("Save of config file failed: "+ex.toString());
            return false;
        }
        return true;
    }

    public String getStrProperty(String key) {
        String s = (String)props.get(key); 
        if( s == null ) {
            return "";
        }
        return s;
    }
    
    public boolean getBooleanProperty(String key) {
        String val = (String)props.get(key);
        boolean b = false;
        try {
            b = new Boolean(val).booleanValue();
        } catch(Exception ex) {
//            BoincNetStats.out("Config value is not a boolean: "+val);
        }
        return b;
    }

    public int getIntProperty(String key) {
        String val = (String)props.get(key);
        int i=-1;
        try {
            i = Integer.parseInt(val);
        } catch(Exception ex) {
//            BoincNetStats.out("Config value is not a number: "+val);
        }
        return i;
    }

    public void setProperty(String k, String v) {
        props.put(k, v);
    }

}
