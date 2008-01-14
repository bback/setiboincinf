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
package boincinf.clientstate;

import org.w3c.dom.*;

import boincinf.util.*;

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

    public boolean initialize(final Element ele)
    {
        timezone = XmlTools.getValidText(ele, "timezone");
        domain_name = XmlTools.getValidText(ele, "domain_name");
        ip_addr = XmlTools.getValidText(ele, "ip_addr");
        final String str_p_ncpus = XmlTools.getValidText(ele, "p_ncpus");
        p_vendor = XmlTools.getValidText(ele, "p_vendor");
        p_model = XmlTools.getValidText(ele, "p_model");
		final String str_p_fpops = XmlTools.getValidText(ele, "p_fpops");
		final String str_p_iops = XmlTools.getValidText(ele, "p_iops");
		final String str_p_membw = XmlTools.getValidText(ele, "p_membw");
		final String str_p_fpop_err = XmlTools.getValidText(ele, "p_fpop_err");
		final String str_p_iop_err = XmlTools.getValidText(ele, "p_iop_err");
		final String str_p_membw_err = XmlTools.getValidText(ele, "p_membw_err");
		final String str_p_calculated = XmlTools.getValidText(ele, "p_calculated");
        os_name = XmlTools.getValidText(ele, "os_name");
        os_version = XmlTools.getValidText(ele, "os_version");
		final String str_m_nbytes = XmlTools.getValidText(ele, "m_nbytes");
		final String str_m_cache = XmlTools.getValidText(ele, "m_cache");
		final String str_m_swap = XmlTools.getValidText(ele, "m_swap");
		final String str_d_total = XmlTools.getValidText(ele, "d_total");
		final String str_d_free = XmlTools.getValidText(ele, "d_free");

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