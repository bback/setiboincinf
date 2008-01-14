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

import java.text.*;
import java.util.*;

public class Util
{
	public static String formatDateTime(long val)
	{
		// values in xml are in seconds, not milliseconds
		val *= 1000;
		final Date d = new Date(val);

		return DateFormat.getDateTimeInstance().format(d);
	}

    public static String formatTime(long val)
    {
        // values in xml are in seconds
        final long hours = val / 60 / 60;
        val = (val - hours*60*60);

        final long minutes = val / 60;
        val = (val - minutes*60);

        final long seconds = val;

        final StringBuffer sb = new StringBuffer();
        sb.append(twoDigitStr(hours)).append(":")
          .append(twoDigitStr(minutes)).append(":")
          .append(twoDigitStr(seconds));

        return sb.toString();
    }

    private static String twoDigitStr(final long val)
    {
        String r = Long.toString(val);
        if( r.length() < 2 )
        {
            r = "0"+r;
        }
        return r;
    }

}
