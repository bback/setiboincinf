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

import java.io.*;
import java.net.*;
import java.sql.*;

import boincinf.*;

public class BoincSetiHostsWebsite {
    String host_info_url = "http://setiweb.ssl.berkeley.edu/hosts_user.php";
    String account_info_url = "http://setiweb.ssl.berkeley.edu/home.php";

    public HostStats getBoincSetiHostsWebsite(final String accountId) throws Exception {
        BoincNetStats.out("Retrieving SETI BOINC host statistics website ...");

        final String host_website = getWebsite(host_info_url, accountId);
        final String account_website = getWebsite(account_info_url, accountId);
        System.out.println("--------------------------");
        System.out.println(host_website);
        System.out.println("--------------------------");
        System.out.println(account_website);
        System.out.println("--------------------------");
        final HostStats hs = extractDataFromHostWebsite(host_website);
        addDeletedHostsToHoststats(account_website, hs);

        return hs;
    }

    private String getWebsite(final String siteurl, final String accountId) throws Exception {
        final URL url = new URL(siteurl);
        final URLConnection conn = url.openConnection();

        conn.setRequestProperty("Cookie", "auth="+accountId+";");

        try {
            conn.connect();
        } catch (final IOException e) {
            final String o = "Connect to webserver failed: "+e.getMessage();
            throw new Exception(o, e);
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        final StringBuffer res = new StringBuffer();
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
    private void addDeletedHostsToHoststats(final String html, final HostStats hs) throws Exception {

        if( html.indexOf("Total credit") < 0 ) {
            final String o = "Received account website contains invalid content, maybe server is overloaded.";
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
        catch(final Exception ex) { throw new Exception("Invalid 'Total credit' value: "+value); }

        final double deleted_hosts_credit = overall_credit - hs.sum_credit;

        // build new HostStats entry
        final String data_hostid = "0";
        final String data_hostname = "(Deleted hosts credits)";
        final String data_avg_credit = "0";
        final String data_credit = ""+deleted_hosts_credit;
        final String data_systemtype = "Misc";
        final String data_ostype = "Misc";

        final int ix = hs.singleHosts.size();
        final SingleHostStat h = new SingleHostStat(
                ix,
                data_hostid,
                data_hostname,
                data_avg_credit,
                data_credit,
                data_systemtype,
                data_ostype);

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

NEW FORMAT:

?hostid=4301547>4301547</a>
        | <a href=results.php?hostid=4301547>tasks</a></nobr>
        </td>
    <td>stimpy</td>
<td>home</td>

<td align=right>650.78</td>
<td align=right>15,567</td>
<td>GenuineIntel<br><span class=note>Intel(R) Core(TM)2 CPU          6300  @ 1.86GHz [Intel64 Family 6 Model 15 Stepping 2]</span></td>
<td>Microsoft Windows Vista<br><span class=note>, Service Pack 1, (06.00.6001.00)</span></td>
<td>8 May 2008 5:28:19 UTC</td>
</tr>
<tr><td>
        <nobr><a href=
    */
    private HostStats extractDataFromHostWebsite(final String html) throws Exception {
        if( html.indexOf("Your computers") < 0 ) {
            final String o = "Received hosts website contains invalid content, maybe the server is overloaded.";
            throw new Exception(o);
        }

        final HostStats hs = new HostStats();
        hs.timestamp = new Timestamp( System.currentTimeMillis() );

        String data_hostid;
        String data_hostname;
        String data_avg_credit;
        String data_credit;
        String data_systemtype;
        String data_ostype;

        final String[] res = html.split("show_host_detail.php");
        for(int x=1; x<res.length; x++) {
            // each string is a computer, x=0 is part before first computer, ignore
            final String comphtml = res[x];

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

            // skip <td>home</td>
            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos) + 1;


            searchStr = "<td";
            actpos = comphtml.indexOf(searchStr, actpos);
            searchStr = ">";
            actpos = comphtml.indexOf(searchStr, actpos);

            p = comphtml.indexOf('<', actpos+1);
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);

            data_avg_credit = normalizeNumber(value.trim());


            searchStr = "<td";
            actpos = comphtml.indexOf(searchStr, actpos);
            searchStr = ">";
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

            data_systemtype = removeString(value.trim(), "<br><span class=note>", " ");
            data_systemtype = removeString(data_systemtype, "</span>", "");

            searchStr = "<td>";
            actpos = comphtml.indexOf(searchStr, actpos);
            p = comphtml.indexOf("</td>", actpos+1); // <br> may be included!!!
            actpos += searchStr.length();
            value = comphtml.substring(actpos, p);

            data_ostype = removeString(value.trim(), "<br><span class=note>", " ");
            data_ostype = removeString(data_ostype, "</span>", "");

            final SingleHostStat h = new SingleHostStat(
                    x,
                    data_hostid,
                    data_hostname,
                    data_avg_credit,
                    data_credit,
                    data_systemtype,
                    data_ostype);

            hs.addSingleHost(h);
        }

        hs.sumSingleHosts();
        return hs;
    }

    private String removeString(String in, final String remove, final String subst) {
        // remove all <br> from string
        while(true) {
            final int p = in.indexOf(remove);
            if( p < 0 ) {
                break;
            }
            String workStr = in.substring(0,p); // part before <br>
            workStr += subst;
            workStr += in.substring(p+remove.length()); // part behind <br>
            in = workStr; // continue search
        }
        return in;
    }

    private String normalizeNumber(final String num) {
        // num=123,456.78  change to 123456.78
        final StringBuffer sb = new StringBuffer();
        for(int x=0; x<num.length(); x++) {
            final char c = num.charAt(x);
            if( c != ',' ) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
