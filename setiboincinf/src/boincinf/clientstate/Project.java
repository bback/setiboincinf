package boincinf.clientstate;

import org.w3c.dom.Element;

import boincinf.util.XmlTools;

/*
<project>
    <scheduler_url>http://setiboinc.ssl.berkeley.edu/sah_cgi/cgi</scheduler_url>
    <master_url>http://setiathome.berkeley.edu/</master_url>
    <project_name>SETI@home</project_name>
    <user_name>bback</user_name>
    <team_name></team_name>
    <email_hash>0a44a0c0fdefc714cef844b0e676fd98</email_hash>
    <cross_project_id></cross_project_id>
    <user_total_credit>0.000000</user_total_credit>
    <user_expavg_credit>0.000000</user_expavg_credit>
    <user_create_time>0</user_create_time>
    <rpc_seqno>208</rpc_seqno>
    <hostid>36007</hostid>
    <host_total_credit>0.000000</host_total_credit>
    <host_expavg_credit>0.000000</host_expavg_credit>
    <host_create_time>1088654872</host_create_time>
    <exp_avg_cpu>23382.078350</exp_avg_cpu>
    <exp_avg_mod_time>12733513575.217957</exp_avg_mod_time>
    <nrpc_failures>8</nrpc_failures>
    <master_fetch_failures>0</master_fetch_failures>
    <min_rpc_time>1089043581</min_rpc_time>
    <code_sign_key> ... </code_sign_key>
</project>
*/

public class Project
{
    String scheduler_url;
    String master_url;
    String project_name;
    String user_name;
    String team_name;
    String email_hash;
    String cross_project_id;
    String host_total_credit;
    String host_expavg_credit;
    String host_create_time;
    
	double user_total_credit;
	double user_expavg_credit;
	long user_create_time;
	long rpc_seqno;
	long hostid;
    
	double exp_avg_cpu;
	double exp_avg_mod_time;
    long nrpc_failures;
    long master_fetch_failures;
    long min_rpc_time;
    
    public boolean initialize(Element ele)
    {
        scheduler_url = XmlTools.getValidText(ele, "scheduler_url");
        master_url = XmlTools.getValidText(ele, "master_url");
        project_name = XmlTools.getValidText(ele, "project_name");
        user_name = XmlTools.getValidText(ele, "user_name");
        team_name = XmlTools.getValidText(ele, "team_name");
        email_hash = XmlTools.getValidText(ele, "email_hash");
        cross_project_id = XmlTools.getValidText(ele, "cross_project_id");
        String str_user_total_credit = XmlTools.getValidText(ele, "user_total_credit");
		String str_user_expavg_credit = XmlTools.getValidText(ele, "user_expavg_credit");
		String str_user_create_time = XmlTools.getValidText(ele, "user_create_time");
		String str_rpc_seqno = XmlTools.getValidText(ele, "rpc_seqno");
		String str_hostid = XmlTools.getValidText(ele, "hostid");
        host_total_credit = XmlTools.getValidText(ele, "host_total_credit");
        host_expavg_credit = XmlTools.getValidText(ele, "host_expavg_credit");
        host_create_time = XmlTools.getValidText(ele, "host_create_time");
		String str_exp_avg_cpu = XmlTools.getValidText(ele, "exp_avg_cpu");
		String str_exp_avg_mod_time = XmlTools.getValidText(ele, "exp_avg_mod_time");
		String str_nrpc_failures = XmlTools.getValidText(ele, "nrpc_failures");
		String str_master_fetch_failures = XmlTools.getValidText(ele, "master_fetch_failures");
		String str_min_rpc_time = XmlTools.getValidText(ele, "min_rpc_time");
		
		user_total_credit = Double.parseDouble(str_user_total_credit);
		user_expavg_credit = Double.parseDouble(str_user_expavg_credit);
		user_create_time = Long.parseLong(str_user_create_time);
		rpc_seqno = Long.parseLong( str_rpc_seqno );
		hostid = Long.parseLong( str_hostid );
    
		exp_avg_cpu = Double.parseDouble(str_exp_avg_cpu);
		exp_avg_mod_time = Double.parseDouble(str_exp_avg_mod_time);
		nrpc_failures = Long.parseLong( str_nrpc_failures );
		master_fetch_failures = Long.parseLong( str_master_fetch_failures );
		min_rpc_time = Long.parseLong( str_min_rpc_time );
        
        return true;
    }
    
}
