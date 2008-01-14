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

/**
 * @author kgraul
 */
public class CreditUpdateData {

    public long hostId;
    public String hostName;
    public int daysSinceLastUpdate;

    public String getValueAt(final int column_index) {
        if (column_index == 0) {
            return ""+hostId;
        }
        if (column_index == 1) {
            return hostName;
        }
        if (column_index == 2) {
            return ""+daysSinceLastUpdate;
        }
        return "*err*";
    }

}
