package boincinf.clientstate;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import boincinf.util.*;

/*
<platform_name>windows_intelx86</platform_name>
<core_client_major_version>3</core_client_major_version>
<core_client_minor_version>19</core_client_minor_version>
*/
public class ClientState
{
    String platform_name;
    String core_client_major_version;
    String core_client_minor_version;
    String host_venue;

    ActiveTaskSet active_task_set;

    ArrayList<Result> resultList;
    ArrayList<Project> projectList;
    HostInfo host_info;

    public void readClientState(final InputStream is)
    {
        final Document doc = XmlTools.parseXmlFile(is, false);
        final Element root = doc.getDocumentElement(); // <client_state>

        Element ele;

        ///////////////////////////////////////////////////////////////////////////////////////
        // host_info
        ele = XmlTools.findFirstElementByTagName(root, "host_info");
        host_info = new HostInfo();
        host_info.initialize(ele);

		///////////////////////////////////////////////////////////////////////////////////////
        // active_task_set with active_task list
        ele = XmlTools.findFirstElementByTagName(root, "active_task_set");
        active_task_set = new ActiveTaskSet();
        active_task_set.initialize(ele, host_info);

		///////////////////////////////////////////////////////////////////////////////////////
        // <workunit> list
        final ArrayList<WorkUnit> workUnitList = readWorkUnits(root);
        System.out.println("Read "+workUnitList.size()+" workunits.");

		///////////////////////////////////////////////////////////////////////////////////////
        // projects
        this.projectList = readProjects(root);
        System.out.println("Read "+projectList.size()+" projects.");

		///////////////////////////////////////////////////////////////////////////////////////
        // others
        platform_name = XmlTools.getValidText(root, "platform_name");
        core_client_major_version = XmlTools.getValidText(root, "core_client_major_version");
        core_client_minor_version = XmlTools.getValidText(root, "core_client_minor_version");

		host_venue = XmlTools.getValidText(root, "host_venue");
		if( host_venue.length() == 0 )
		{
			host_venue = "default";
		}

		///////////////////////////////////////////////////////////////////////////////////////
		// <result> list
		this.resultList = readResults(root);
		System.out.println("Read "+resultList.size()+" results.");

		///////////////////////////////////////////////////////////////////////////////////////
		// join active tasks into results
		// each result get its ActiveTask
		final Iterator<Result> j = resultList.iterator();
		while(j.hasNext())
		{
			final Result res = j.next();
			for(int x=0; x<active_task_set.getActiveTasksCount(); x++)
			{
				final ActiveTask at = active_task_set.getActiveTask(x);
				if( res.name.equals( at.result_name ) )
				{
					res.activeTask = at;
				}
			}
		}

		///////////////////////////////////////////////////////////////////////////////////////
		// join workunit and result by wu_name
		// each workunit get its results in a list
		final Iterator<Result> i2 = resultList.iterator();
		final ArrayList<WorkUnit> removedWorkunits = new ArrayList<WorkUnit>();
		while(i2.hasNext())
		{
			final Result res = i2.next();
			final Iterator<WorkUnit> i = workUnitList.iterator();
			while(i.hasNext())
			{
				final WorkUnit wu = i.next();
				if( res.wu_name.equals( wu.name ) )
				{
					if( res.work_unit != null )
					{
						System.out.println("Error: Result had already a workunit assigned!");
					}
					res.work_unit = wu;
					removedWorkunits.add( wu );
				}
			}
		}
		workUnitList.removeAll(removedWorkunits);
		if( workUnitList.size() > 0 )
		{
			System.out.println("Warning: "+resultList.size()+" workunits seem to not have a result!");
		}
    }

    private ArrayList<Project> readProjects(final Element root)
    {
		final ArrayList<Project> lProjectList = new ArrayList<Project>();

        final List<Element> l = XmlTools.getChildElementsByTagName(root, "project");
        final Iterator<Element> i = l.iterator();
        while( i.hasNext() )
        {
            final Element pEle = i.next();
            final Project p = new Project();
            p.initialize( pEle );

            lProjectList.add( p );
        }
        return lProjectList;
    }

    private ArrayList<WorkUnit> readWorkUnits(final Element root)
    {
		final ArrayList<WorkUnit> workUnitList = new ArrayList<WorkUnit>();

        final List<Element> l = XmlTools.getChildElementsByTagName(root, "workunit");
        final Iterator<Element> i = l.iterator();
        while( i.hasNext() )
        {
            final Element wuEle = i.next();
            final WorkUnit wu = new WorkUnit();
            wu.initialize( wuEle, host_info );

            workUnitList.add( wu );
        }
        return workUnitList;
    }

    private ArrayList<Result> readResults(final Element root)
    {
		final ArrayList<Result> lResultList = new ArrayList<Result>();

        final List<Element> l = XmlTools.getChildElementsByTagName(root, "result");
        final Iterator<Element> i = l.iterator();
        while( i.hasNext() )
        {
            final Element rEle = i.next();
            final Result r = new Result();
            r.initialize( rEle );

            lResultList.add( r );
        }
        return lResultList;
    }

    public int getResultCount()
    {
        return resultList.size();
    }

    public Result getResult(final int t)
    {
        return resultList.get(t);
    }

    public int getProjectCount()
    {
        return projectList.size();
    }

    public Project getProject(final int t)
    {
        return projectList.get(t);
    }

}
