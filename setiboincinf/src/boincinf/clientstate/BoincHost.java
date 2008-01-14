package boincinf.clientstate;

import java.io.*;
import java.net.Socket;

public class BoincHost
{
	String hostname = null;
	File file = null; // if there is a client_state.xml file we prefer to read this instead doing rpc calls
	ClientState clientState = null;
	Messages msgs = null;

// TODO: use 1 socket only

	public BoincHost(String hostn, File filen)
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
			catch(FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			is = bis;
		}
		else if( hostname != null )
		{
			String rpc = "<get_state/>";
			byte[] answer = doGuiRpc(hostname, rpc);
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

		int maxMsg = 500;
        
		String rpc = getMsgsRpc(msgs.getHighestSeqno(), maxMsg);
		byte[] answer = doGuiRpc(hostname, rpc);
//		int read = 
        msgs.readMessages( new ByteArrayInputStream(answer) );
		
		return true;
	}
	
	private String getMsgsRpc(int startSeqNo, int maxMsgNo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<get_messages>");
		sb.append("<nmessages>").append(maxMsgNo).append("<nmessages>");
		sb.append("<seqno>").append(startSeqNo).append("<seqno>");
		sb.append("</get_messages>");
		return sb.toString();
	}
	
	private byte[] doGuiRpc(String host, String rpc)
	{
		byte[] result = null;
		try {
			Socket s = new Socket(host, 31416);
			s.getOutputStream().write( rpc.getBytes() );
            
			BufferedInputStream i = new BufferedInputStream( s.getInputStream() );
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
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
		catch(Exception ex)
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
