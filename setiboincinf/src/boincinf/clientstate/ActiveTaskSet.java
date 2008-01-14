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

import java.util.*;

import org.w3c.dom.*;

import boincinf.util.*;

/*
<active_task_set>
    <active_task>
    <project_master_url>http://setiathome.berkeley.edu/</project_master_url>
    <result_name>01ja04aa.2129.25888.897148.128_2</result_name>
    <app_version_num>308</app_version_num>
    <slot>0</slot>
    <checkpoint_cpu_time>15328.951951</checkpoint_cpu_time>
    <fraction_done>0.724481</fraction_done>
    <current_cpu_time>15328.952000</current_cpu_time>
    </active_task>
</active_task_set>

*/

public class ActiveTaskSet
{
    private ArrayList<ActiveTask> activeTasksList;

    public int getActiveTasksCount()
    {
        return activeTasksList.size();
    }

    public ActiveTask getActiveTask(final int t)
    {
        return activeTasksList.get(t);
    }

    public boolean initialize(final Element ele, final HostInfo hi)
    {
        activeTasksList = new ArrayList<ActiveTask>();

        final List<Element> l = XmlTools.getChildElementsByTagName(ele, "active_task");
        final Iterator<Element> i = l.iterator();
        while( i.hasNext() )
        {
            final Element aChild = i.next();
            final ActiveTask at = new ActiveTask();
            at.initialize(aChild, hi);

            activeTasksList.add( at );
        }
        return true;
    }
}
