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
<workunit>
    <name>01ja04aa.2129.25888.897148.128</name>
    <app_name>setiathome</app_name>
    <version_num>308</version_num>
    <command_line></command_line>
    <env_vars></env_vars>
    <rsc_fpops_est>27924800000000.000000</rsc_fpops_est>
    <rsc_fpops_bound>446797000000000.000000</rsc_fpops_bound>
    <rsc_memory_bound>33554422.000000</rsc_memory_bound>
    <rsc_disk_bound>500000.000000</rsc_disk_bound>
    <file_ref>
        <file_name>01ja04aa.2129.25888.897148.128</file_name>
        <open_name>work_unit.sah</open_name>
    </file_ref>
</workunit>
*/

public class WorkUnit
{
    String name;
    String app_name;
    String version_num;

    double rsc_fpops_est;
    double rsc_fpops_bound;
    double rsc_memory_bound;
    double rsc_disk_bound;

    // COMPUTED:
    long estimated_cpu_time;

    public boolean initialize(final Element ele, final HostInfo hi)
    {
        name = XmlTools.getValidText(ele, "name");
        app_name = XmlTools.getValidText(ele, "app_name");
        version_num = XmlTools.getValidText(ele, "version_num");
        final String str_rsc_fpops_est = XmlTools.getValidText(ele, "rsc_fpops_est");
		final String str_rsc_fpops_bound = XmlTools.getValidText(ele, "rsc_fpops_bound");
		final String str_rsc_memory_bound = XmlTools.getValidText(ele, "rsc_memory_bound");
		final String str_rsc_disk_bound = XmlTools.getValidText(ele, "rsc_disk_bound");

		rsc_fpops_est = Double.parseDouble(str_rsc_fpops_est);
		rsc_fpops_bound = Double.parseDouble(str_rsc_fpops_bound);
		rsc_memory_bound = Double.parseDouble(str_rsc_memory_bound);
		rsc_disk_bound = Double.parseDouble(str_rsc_disk_bound);

        estimated_cpu_time = (long)(rsc_fpops_est / hi.p_fpops);

        return true;
    }
}
