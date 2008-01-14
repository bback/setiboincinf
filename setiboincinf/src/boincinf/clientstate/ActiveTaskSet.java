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
