package boincinf.netstat;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;

import boincinf.BoincNetStats;

public class BoincSetiHostsWebsite
{
    String host_info_url = "http://setiweb.ssl.berkeley.edu/hosts_user.php";
    String account_info_url = "http://setiweb.ssl.berkeley.edu/home.php";
    
//    public static void main(String[] args) throws Throwable {
//        String accId = "6f1db02360deb967b5b6f66e853eba92";
//        BoincSetiHostsWebsite ws = new BoincSetiHostsWebsite(); 
//        HostStats hs = ws.getBoincSetiHostsWebsite(accId);
//    }
    
    public HostStats getBoincSetiHostsWebsite(String accountId) throws Exception
    {
        BoincNetStats.out("Retrieving SETI BOINC host statistics website ...");
        
        String host_website = getWebsite(host_info_url, accountId);
        String account_website = getWebsite(account_info_url, accountId);
        System.out.println("--------------------------");
        System.out.println(host_website);
        System.out.println("--------------------------");
        System.out.println(account_website);
        System.out.println("--------------------------");
        HostStats hs = extractDataFromHostWebsite(host_website);
        addDeletedHostsToHoststats(account_website, hs);

        return hs;
    }
    
    private String getWebsite(String siteurl, String accountId) throws Exception {
        URL url = new URL(siteurl);
        URLConnection conn = url.openConnection();
        
        conn.setRequestProperty("Cookie", "auth="+accountId+";");
        
        try {
            conn.connect();
        } catch (IOException e) {
            String o = "Connect to webserver failed: "+e.getMessage();
            throw new Exception(o, e);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuffer res = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            res.append(inputLine).append("\n");
        }
        in.close();
        return res.toString();
    }
/*    
    public String readHtmlFile(File f) throws Throwable
    {
        BufferedReader in = new BufferedReader(new FileReader(f));

        StringBuffer res = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            res.append(inputLine).append("\n");
        }
        in.close();
        return res.toString();  
    }
*/

    /*
     * Because deleted hosts are missed in my overall credits my overall credits are
     * lesser than setis overall credits.
     * Now we get out the overall credits from account info website and
     * create an host entry for the sum of missing computers credits.
     * 
    <tr><td width=40% class=fieldname>Total credit</td><td class=fieldvalue>128,720.26</td></tr>
    */
    private void addDeletedHostsToHoststats(String html, HostStats hs) throws Exception {

        if( html.indexOf("Total credit") < 0 )
        {
            String o = "Received account website contains invalid content, maybe server is overloaded.";
            throw new Exception(o);
        }
        
        // extract total credit value string
        String searchStr;
        int actpos;
        String value;
        int p;
    
        searchStr = "Total credit";
        actpos = html.indexOf(searchStr);
        actpos += searchStr.length();
        
        searchStr = "<td";
        actpos = html.indexOf(searchStr, actpos);
        actpos += searchStr.length();

        searchStr = ">";
        actpos = html.indexOf(searchStr, actpos);
        p = html.indexOf('<', actpos+1);
        actpos += searchStr.length();
        value = html.substring(actpos, p);

        value = normalizeNumber(value);
        double overall_credit;
        try { overall_credit = Double.parseDouble(value); } 
        catch(Exception ex) { throw new Exception("Invalid 'Total credit' value: "+value); }
        
        double deleted_hosts_credit = overall_credit - hs.sum_credit;
        
        // build new HostStats entry
        String data_hostid = "0";
        String data_hostname = "(Deleted hosts credits)";
        String data_avg_credit = "0";
        String data_credit = ""+deleted_hosts_credit;
        String data_systemtype = "Misc";
        String data_ostype = "Misc";
        
        int ix = hs.singleHosts.size();
        SingleHostStat h = new SingleHostStat(ix, data_hostid, data_hostname, data_avg_credit, data_credit,
                data_systemtype, data_ostype);

        hs.addSingleHost(h);
        //hs.sum_credit = overall_credit;
        /// re-sum after adding a new line
        hs.sumSingleHosts();
    }
    
    /*
     >?hostid=36007>36007</a></td>
    <td> REN
            <td>5.99</td>
            <td>48.26</td>
            <td>GenuineIntel 696MHz Pentium</td>
            <td>Microsoft Windows 2000 Professional Edition, Service Pack 4, (05.00.2195.00)</td></tr>
    <tr><td><a href=<
    */          
    private HostStats extractDataFromHostWebsite(String html) throws Exception
    {
        if( html.indexOf("Your computers") < 0 )
        {
            String o = "Received hosts website contains invalid content, maybe server is overloaded.";
            throw new Exception(o);
        }
        HostStats hs = new HostStats();
        hs.timestamp = new Timestamp( System.currentTimeMillis() );
        
        String data_hostid;
        String data_hostname;
        String data_avg_credit;
        String data_credit;
        String data_systemtype;
        String data_ostype;
        
        String[] res = html.split("show_host_detail.php");
        for(int x=1; x<res.length; x++)
        {
            // each string is a computer, x=0 is part before first computer, ignore
            String comphtml = res[x];
            
            String searchStr;
            int actpos;
            String value;
            int p;
        
            searchStr = "hostid=";
            actpos = comphtml.indexOf(searchStr);
            p = comphtml.indexOf('>', actpos+1);
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_hostid = value.trim();
            
            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf('<', actpos+1);
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_hostname = value.trim();
            
            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf('<', actpos+1);
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_avg_credit = normalizeNumber(value.trim());         

            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf('<', actpos+1);
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_credit = normalizeNumber(value.trim());         

            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf("</td>", actpos+1); // <br> may be included!!!
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_systemtype = removeBR(value.trim());         

            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf("</td>", actpos+1); // <br> may be included!!!
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);
            
            data_ostype = removeBR(value.trim());
            
            SingleHostStat h = new SingleHostStat(x, data_hostid, data_hostname, data_avg_credit, data_credit,
                                            data_systemtype, data_ostype);
            
            hs.addSingleHost(h);            
        }
        hs.sumSingleHosts();
        return hs;  
    }
    
    private String removeBR(String in) {
        // remove all <br> from string
        while(true) {
            int p = in.indexOf("<br>");
            if( p < 0 ) {
                break;
            }
            String workStr = in.substring(0,p); // part before <br>
            workStr += in.substring(p+"<br>".length()+1); // part behind <br>, and skip 1 space
            in = workStr; // continue search
        }
        return in;
    }
    
    private String normalizeNumber(String num) {
        // num=123,456.78  change to 123456.78
        StringBuffer sb = new StringBuffer();
        for(int x=0; x<num.length(); x++) {
            char c = num.charAt(x);
            if( c != ',' ) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
