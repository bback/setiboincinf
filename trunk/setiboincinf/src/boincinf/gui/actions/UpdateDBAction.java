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
package boincinf.gui.actions;

import org.eclipse.jface.action.*;

import boincinf.*;
import boincinf.gui.*;
import boincinf.netstat.*;

public class UpdateDBAction extends Action {
    BoincInfGui window;
    SQLStorage store;

    public UpdateDBAction(final BoincInfGui s, final SQLStorage st) {
        window = s;
        store = st;
        setText("Update from updateloop database");
        setToolTipText("Update from updateloop database");
//        setImageDescriptor(Util.getDescriptor("update"));
    }

    @Override
    public void run() {
        try {
            window.autoDatabaseUpdate(true);
        } catch(final Exception ex) {
            BoincNetStats.out("Error: "+ex.toString());
        }
    }
}
