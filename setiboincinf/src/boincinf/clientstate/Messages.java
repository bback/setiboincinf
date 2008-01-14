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

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import boincinf.util.*;


/*
<msgs>
    <msg>
        <pri>x</pri>
        <seqno>x</seqno>
        <body>
        x
        </body>
        <time>x</time>
    </msg>
    ...
</msgs>
*/
public class Messages {
    private final ArrayList<Message> messagesList = new ArrayList<Message>();
    private int highestSeqno = 0;

    public int getHighestSeqno() {
        return highestSeqno;
    }

    public int getMessageCount() {
        return messagesList.size();
    }

    public Message getMessageTask(final int t) {
        return messagesList.get(t);
    }

    public int readMessages(final InputStream is) {
        final Document doc = XmlTools.parseXmlFile(is, false);
        final Element root = doc.getDocumentElement(); // <msgs>

        return readMsgs(root);
    }

    private int readMsgs(final Element ele) {
        final List<Element> l = XmlTools.getChildElementsByTagName(ele, "msg");
        final Iterator<Element> i = l.iterator();
        final ArrayList<Message> newMsgs = new ArrayList<Message>();
        while( i.hasNext() ) {
            final Element aChild = i.next();
            final Message m = new Message();
            m.initialize(aChild);

            if( m.seqno > highestSeqno ) {
                highestSeqno = m.seqno;
            }

            newMsgs.add(m);
        }

        // insert new messages at begin of old messages
        messagesList.addAll(0, newMsgs);

        return newMsgs.size();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for( int x = messagesList.size() - 1; x >= 0; x-- ) {
            final Message m = messagesList.get(x);
            sb.append("[").append(Util.formatDateTime(m.time)).append("] ");
            sb.append(m.seqno).append(" - ");
            sb.append(m.body);
            sb.append("\n");
        }
        return sb.toString();
    }
}
