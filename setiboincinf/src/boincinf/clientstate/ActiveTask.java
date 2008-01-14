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
<active_task>
    <project_master_url>http://setiathome.berkeley.edu/</project_master_url>
    <result_name>01ja04aa.2129.25888.897148.128_2</result_name>
    <app_version_num>308</app_version_num>
    <slot>0</slot>
    <checkpoint_cpu_time>15328.951951</checkpoint_cpu_time>
    <fraction_done>0.724481</fraction_done>
    <current_cpu_time>15328.952000</current_cpu_time>
</active_task>
*/
public class ActiveTask
{
    String project_master_url;
    String result_name;
    String app_version_num;

	int slot;

	float fraction_done;
	long checkpoint_cpu_time; // counted cpu time when system crashes
	long current_cpu_time;

    // COMPUTED:
    long remaining_cpu_time; // remaining Time before result is computed
    long completion_time; // DateTime when result will be computed
    long expected_cpu_time; // expected cpu Time

    public boolean initialize(final Element ele, final HostInfo hi)
    {
        project_master_url = XmlTools.getValidText(ele, "project_master_url");
        result_name = XmlTools.getValidText(ele, "result_name");
        app_version_num = XmlTools.getValidText(ele, "app_version_num");
        final String str_slot = XmlTools.getValidText(ele, "slot");
		final String str_checkpoint_cpu_time = XmlTools.getValidText(ele, "checkpoint_cpu_time");
		final String str_fraction_done = XmlTools.getValidText(ele, "fraction_done");
		final String str_current_cpu_time = XmlTools.getValidText(ele, "current_cpu_time");

		slot = Integer.parseInt(str_slot);

		fraction_done = Float.parseFloat(str_fraction_done);
		checkpoint_cpu_time = (long)Double.parseDouble(str_checkpoint_cpu_time);
		current_cpu_time = (long)Double.parseDouble(str_current_cpu_time);

        remaining_cpu_time = (long)(checkpoint_cpu_time - (checkpoint_cpu_time * fraction_done));
        completion_time = (System.currentTimeMillis() / 1000) + remaining_cpu_time;
        expected_cpu_time = checkpoint_cpu_time + remaining_cpu_time;

        float actual_claimed_credit;
        final double daysecs = (24 * 60 * 60)/100;
        actual_claimed_credit = (float)( (checkpoint_cpu_time * hi.p_fpops)/daysecs);

        System.out.println("claimed="+actual_claimed_credit);

        return true;
    }
}
