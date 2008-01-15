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
package boincinf.netstat;

public class Averages {

    private final String avg14;
    private final String avg14_grow;
    private final String avg30;
    private final String avg30_grow;
    private final String avgAll;
    private final String avgAll_grow;

    public Averages(
            final String newAvg14,
            final String newAvg14_grow,
            final String newAvg30,
            final String newAvg30_grow,
            final String newAvgAll,
            final String newAvgAll_grow)
    {
        avg14 = newAvg14;
        avg14_grow = newAvg14_grow;
        avg30 = newAvg30;
        avg30_grow = newAvg30_grow;
        avgAll = newAvgAll;
        avgAll_grow = newAvgAll_grow;
    }

    public String getAvg14() {
        return avg14;
    }

    public String getAvg14_grow() {
        return avg14_grow;
    }

    public String getAvg30() {
        return avg30;
    }

    public String getAvg30_grow() {
        return avg30_grow;
    }

    public String getAvgAll() {
        return avgAll;
    }

    public String getAvgAll_grow() {
        return avgAll_grow;
    }
}