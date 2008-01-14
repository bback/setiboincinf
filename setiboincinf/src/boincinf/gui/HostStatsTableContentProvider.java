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
package boincinf.gui;

import java.util.*;

import org.eclipse.jface.viewers.*;

public class HostStatsTableContentProvider implements IStructuredContentProvider {
    public Object[] getElements(final Object element) {
        // called after setInput
        final ArrayList v = (ArrayList)element;
        return v.toArray();
    }

    public void dispose() {}

    public void inputChanged(final Viewer viewer, final Object old_object, final Object new_object) {}
}
