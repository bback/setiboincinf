package boincinf.clientstate;

import org.w3c.dom.Element;

import boincinf.util.XmlTools;


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
    
    public boolean initialize(Element ele)
    {
        String strPri = XmlTools.getValidText(ele, "pri");
		String strSeqno = XmlTools.getValidText(ele, "seqno");
        this.body = XmlTools.getValidText(ele, "body");
		String strTime = XmlTools.getValidText(ele, "time");
        
        this.pri = Integer.parseInt(strPri);
		this.seqno = Integer.parseInt(strSeqno);
		this.time = Long.parseLong(strTime);
        
        return true;
    }
}
