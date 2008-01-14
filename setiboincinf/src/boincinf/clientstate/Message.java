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
<msg>
    <pri>x</pri>
    <seqno>x</seqno>
    <body>
    x
    </body>
    <time>x</time>
</msg>
*/

public class Message
{
    String body;

    int pri;
    int seqno;
    long time;

    public boolean initialize(final Element ele)
    {
        final String strPri = XmlTools.getValidText(ele, "pri");
		final String strSeqno = XmlTools.getValidText(ele, "seqno");
        this.body = XmlTools.getValidText(ele, "body");
		final String strTime = XmlTools.getValidText(ele, "time");

        this.pri = Integer.parseInt(strPri);
		this.seqno = Integer.parseInt(strSeqno);
		this.time = Long.parseLong(strTime);

        return true;
    }
}
