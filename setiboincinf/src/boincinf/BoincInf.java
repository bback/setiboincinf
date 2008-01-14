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
package boincinf;

import java.io.*;

import boincinf.clientstate.*;

public class BoincInf {

    public static void main(final String[] args) {
//        new BoincInf().runFile();
//        new BoincInf().runRpc("192.168.0.100");
		new BoincInf().runRpc("127.0.0.1");
    }

    public void runFile() {
        // get info from client_state.xml
		final File xmlfile = new File("c:\\projects\\boincinf\\samples\\client_state.xml");

        final BoincHost bhost = new BoincHost(null, xmlfile);
		process(bhost);
    }

    private void process(final BoincHost bhost) {
		bhost.refreshClientState();
//		bhost.refreshMessages();
//
//		Messages msgs = bhost.getMsgs();
//
//		System.out.println("Last "+msgs.getMessageCount()+" messages:");
//		System.out.println(msgs.toString());
    }

    public void runRpc(final String host) {
    	final BoincHost bhost = new BoincHost( host, null );
    	process(bhost);
    }
}
