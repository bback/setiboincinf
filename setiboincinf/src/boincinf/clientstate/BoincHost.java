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
import java.net.*;

public class BoincHost
{
	String hostname = null;
	File file = null; // if there is a client_state.xml file we prefer to read this instead doing rpc calls
	ClientState clientState = null;
	Messages msgs = null;

// TODO: use 1 socket only

	public BoincHost(final String hostn, final File filen)
	{
		hostname = hostn;
		file = filen;

		msgs = new Messages();
	}

	public boolean refreshClientState()
	{
		InputStream is = null;

		if( file != null )
		{
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream( new FileInputStream(file));
			}
			catch(final FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			is = bis;
		}
		else if( hostname != null )
		{
			final String rpc = "<get_state/>";
			final byte[] answer = doGuiRpc(hostname, rpc);
			is = new ByteArrayInputStream(answer);
		}
		else
		{
			System.out.println("initializeClientState: No hostname and no filename given.");
			return false;
		}

		clientState = new ClientState();
		clientState.readClientState( is );

		return true;
	}

	public boolean refreshMessages()
	{
		// appends new messages to existing msg list

		if( hostname == null )
		{
			System.out.println("initializeMessages: No hostname given.");
			return false;
		}

		final int maxMsg = 500;

		final String rpc = getMsgsRpc(msgs.getHighestSeqno(), maxMsg);
		final byte[] answer = doGuiRpc(hostname, rpc);
//		int read =
        msgs.readMessages( new ByteArrayInputStream(answer) );

		return true;
	}

	private String getMsgsRpc(final int startSeqNo, final int maxMsgNo)
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("<get_messages>");
		sb.append("<nmessages>").append(maxMsgNo).append("<nmessages>");
		sb.append("<seqno>").append(startSeqNo).append("<seqno>");
		sb.append("</get_messages>");
		return sb.toString();
	}

	private byte[] doGuiRpc(final String host, final String rpc)
	{
		byte[] result = null;
		try {
			final Socket s = new Socket(host, 31416);
			s.getOutputStream().write( rpc.getBytes() );

			final BufferedInputStream i = new BufferedInputStream( s.getInputStream() );
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int dat = i.read();
			while( dat != 0x03 )
			{
				if( dat < 0 )
				{
					System.out.println("Unexpected end of input from gui.");
					return null;
				}
				bout.write(dat);
				dat = i.read();
			}
			System.out.println("Read rpc answer.");
			result = bout.toByteArray();
		}
		catch(final Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

    public ClientState getClientState()
    {
        return clientState;
    }

    public String getHostname()
    {
        return hostname;
    }

    public Messages getMsgs()
    {
        return msgs;
    }

}
