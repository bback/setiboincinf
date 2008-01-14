package boincinf.clientstate;

import org.w3c.dom.Element;

import boincinf.util.XmlTools;

/*
<result>
    <name>01ja04aa.2129.25888.897148.128_2</name>
    <final_cpu_time>0.000000</final_cpu_time>
    <exit_status>0</exit_status>
    <state>2</state>
    <wu_name>01ja04aa.2129.25888.897148.128</wu_name>
    <report_deadline>1090059740</report_deadline>
    <file_ref>
        <file_name>01ja04aa.2129.25888.897148.128_2_0</file_name>
        <open_name>result.sah</open_name>
    </file_ref>
</result>
 */
public class Result
{
	// result states:
	
	// New result, files may still need to be downloaded
	public final static int RESULT_NEW               = 0;
	// Input files for result are being downloaded
	public final static int RESULT_FILES_DOWNLOADING = 1;
	// Files are downloaded, result can be computed
	public final static int RESULT_FILES_DOWNLOADED  = 2;
	// Computation is done, if no error then files need to be uploaded
	public final static int RESULT_COMPUTE_DONE      = 3;
	// Output files for result are being uploaded
	public final static int RESULT_FILES_UPLOADING   = 4;
	// Files are uploaded, notify scheduling server	
	public final static int RESULT_FILES_UPLOADED    = 5;
	
    String name;
    String wu_name;

	long final_cpu_time;
	long report_deadline;

	int state;
	int exit_status;
	
	ActiveTask activeTask = null; // set if this result is currently computed
	WorkUnit work_unit = null;
    
    // state: 2 = todo / work ; 5 = ready to report
    
    public boolean initialize(Element ele)
    {
        // = XmlTools.getValidText(ele, "");
        name = XmlTools.getValidText(ele, "name");
        String str_final_cpu_time = XmlTools.getValidText(ele, "final_cpu_time");
		String str_exit_status = XmlTools.getValidText(ele, "exit_status");
		String str_state = XmlTools.getValidText(ele, "state");
        wu_name = XmlTools.getValidText(ele, "wu_name");
		String str_report_deadline = XmlTools.getValidText(ele, "report_deadline");

		final_cpu_time = (long)Double.parseDouble(str_final_cpu_time);
		report_deadline = Long.parseLong(str_report_deadline);

		state = Integer.parseInt(str_state);
		exit_status = Integer.parseInt(str_exit_status);

        return true;
    }
    
    public static String getStateString(int state)
    {
		if( state == RESULT_NEW )
		{
			return "New work";              
		}
		if( state == RESULT_FILES_DOWNLOADING )
		{
			return "Downloading";
		}
		if( state == RESULT_FILES_DOWNLOADED )
		{
			return "Ready to run";
		}
		if( state == RESULT_COMPUTE_DONE )
		{
			return "Ready to report";
		}   
		if( state == RESULT_FILES_UPLOADING )
		{
			return "Uploading";
		}
		if( state == RESULT_FILES_UPLOADED )
		{
			return "Reported";
		}
		return "*err*";
    }
}
