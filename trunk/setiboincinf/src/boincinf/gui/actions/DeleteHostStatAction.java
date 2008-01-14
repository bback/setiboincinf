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

import java.util.*;

import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import boincinf.gui.*;
import boincinf.netstat.*;

public class DeleteHostStatAction extends Action {
    BoincInfGui window;
    SQLStorage store;

    public DeleteHostStatAction(final BoincInfGui w, final SQLStorage s) {
        store = s;
        window = w;
        setToolTipText("Delete selected host statistics entries");
        setText("Delete entries@Ctrl+D");
        setImageDescriptor(Util.getDescriptor("delete"));
    }

    @Override
    public void run() {
        final IStructuredSelection selection = window.getTableSelection();
        if (selection.isEmpty()) {
            return;
        }
        final MessageBox mb = new MessageBox(window.getShell(), SWT.OK | SWT.CANCEL | SWT.ICON_WARNING);
        mb.setMessage("Really delete " + selection.size() + " selected entries?");
        if (mb.open() != SWT.OK) {
            return;
        }

        int del = 0;
        for (final Iterator<HostStats> i = selection.iterator(); i.hasNext();) {
            final HostStats hs = i.next();
            del += store.deleteHostStat(hs);
            window.removeTableRow(hs);
        }
        window.setStatus("Deleted " + del + " entries.");

        window.updateAverages();
    }
}
