package boincinf.clientstate;

import org.w3c.dom.Element;

import boincinf.util.XmlTools;

/*
<host_info>
    <timezone>3600</timezone>
    <domain_name>REN</domain_name>
    <ip_addr>192.168.0.200</ip_addr>
    <p_ncpus>1</p_ncpus>
    <p_vendor>GenuineIntel 697MHz</p_vendor>
    <p_model>Pentium</p_model>
    <p_fpops>797482543.562826</p_fpops>
    <p_iops>1448967584.788767</p_iops>
    <p_membw>1000000000.000000</p_membw>
    <p_fpop_err>0</p_fpop_err>
    <p_iop_err>0</p_iop_err>
    <p_membw_err>0</p_membw_err>
    <p_calculated>12733507890.083134</p_calculated>
    <os_name>Microsoft Windows 2000</os_name>
    <os_version>Professional Edition, Service Pack 4, (05.00.2195.00)</os_version>
    <m_nbytes>536330240.000000</m_nbytes>
    <m_cache>1000000.000000</m_cache>
    <m_swap>904404992.000000</m_swap>
    <d_total>23354933248.000000</d_total>
    <d_free>15889072128.000000</d_free>
</host_info>
*/

public class HostInfo
{
    String timezone;
    String domain_name;
    String ip_addr;
    String p_vendor;
    String p_model;
	String os_name;
	String os_version;

	int p_ncpus; // int
    
	double p_fpops; // double
	double p_iops; // double
	double p_membw; // double
    int p_fpop_err; // int
    int p_iop_err; // int 
    int p_membw_err; // int
	double p_calculated; // double
	double m_nbytes; // double
	double m_cache; // double
	double m_swap; // double
	double d_total; // double
	double d_free; // double
    
    public boolean initialize(Element ele)
    {
        timezone = XmlTools.getValidText(ele, "timezone");
        domain_name = XmlTools.getValidText(ele, "domain_name");
        ip_addr = XmlTools.getValidText(ele, "ip_addr");
        String str_p_ncpus = XmlTools.getValidText(ele, "p_ncpus");
        p_vendor = XmlTools.getValidText(ele, "p_vendor");
        p_model = XmlTools.getValidText(ele, "p_model");
		String str_p_fpops = XmlTools.getValidText(ele, "p_fpops");
		String str_p_iops = XmlTools.getValidText(ele, "p_iops");
		String str_p_membw = XmlTools.getValidText(ele, "p_membw");
		String str_p_fpop_err = XmlTools.getValidText(ele, "p_fpop_err");
		String str_p_iop_err = XmlTools.getValidText(ele, "p_iop_err");
		String str_p_membw_err = XmlTools.getValidText(ele, "p_membw_err");
		String str_p_calculated = XmlTools.getValidText(ele, "p_calculated");
        os_name = XmlTools.getValidText(ele, "os_name");
        os_version = XmlTools.getValidText(ele, "os_version");
		String str_m_nbytes = XmlTools.getValidText(ele, "m_nbytes");
		String str_m_cache = XmlTools.getValidText(ele, "m_cache");
		String str_m_swap = XmlTools.getValidText(ele, "m_swap");
		String str_d_total = XmlTools.getValidText(ele, "d_total");
		String str_d_free = XmlTools.getValidText(ele, "d_free");
		
		p_ncpus = Integer.parseInt(str_p_ncpus);
		p_fpops = Double.parseDouble(str_p_fpops);
		p_iops = Double.parseDouble(str_p_iops);
		p_membw = Double.parseDouble(str_p_membw);
		p_fpop_err = Integer.parseInt(str_p_fpop_err);
		p_iop_err = Integer.parseInt(str_p_iop_err); 
		p_membw_err = Integer.parseInt(str_p_membw_err);
		p_calculated = Double.parseDouble(str_p_calculated);
		m_nbytes = Double.parseDouble(str_m_nbytes);
		m_cache = Double.parseDouble(str_m_cache);
		m_swap = Double.parseDouble(str_m_swap);
		d_total = Double.parseDouble(str_d_total);
		d_free = Double.parseDouble(str_d_free);
        
        return true;
    }    
}