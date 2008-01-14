package boincinf.clientstate;

import java.text.DateFormat;
import java.util.*;

public class Util
{
	public static String formatDateTime(long val)
	{
		// values in xml are in seconds, not milliseconds
		val *= 1000;
		Date d = new Date(val);
		
		return DateFormat.getDateTimeInstance().format(d);
	}
    
    public static String formatTime(long val)
    {
        // values in xml are in seconds
        long hours = val / 60 / 60;
        val = (val - hours*60*60);
        
        long minutes = val / 60;
        val = (val - minutes*60);
        
        long seconds = val;
        
        StringBuffer sb = new StringBuffer();
        sb.append(twoDigitStr(hours)).append(":")
          .append(twoDigitStr(minutes)).append(":")
          .append(twoDigitStr(seconds));
        
        return sb.toString();
    }
    
    private static String twoDigitStr(long val)
    {
        String r = Long.toString(val);
        if( r.length() < 2 )
        {
            r = "0"+r;
        }
        return r;
    }

}
