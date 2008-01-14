package boincinf;

import java.io.File;

import boincinf.clientstate.BoincHost;

public class BoincInf
{
    public static void main(String[] args)
    {
//        new BoincInf().runFile();
//        new BoincInf().runRpc("192.168.0.100");
		new BoincInf().runRpc("127.0.0.1");
    }
    
    public void runFile()
    {
        // get info from client_state.xml
		File xmlfile = new File("c:\\projects\\boincinf\\samples\\client_state.xml");
		
        BoincHost bhost = new BoincHost(null, xmlfile);
		process(bhost);
    }
    
    private void process(BoincHost bhost)
    {
		bhost.refreshClientState();
//		bhost.refreshMessages();
//    	
//		Messages msgs = bhost.getMsgs();
//
//		System.out.println("Last "+msgs.getMessageCount()+" messages:");
//		System.out.println(msgs.toString());
    }
    
    public void runRpc(String host)
    {
    	BoincHost bhost = new BoincHost( host, null );
    	process(bhost);
    }
    
    
}
