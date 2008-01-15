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

public class CreditsAtTime {

    private final double sum_credits;
    private final double sum_avg_credits;
    private final java.sql.Timestamp timestamp;

    public CreditsAtTime(final double newSum_credits, final double newSum_avg_credits, final java.sql.Timestamp newTimestamp) {
        sum_credits = newSum_credits;
        sum_avg_credits = newSum_avg_credits;
        timestamp = newTimestamp;
    }

    public double getSum_credits() {
        return sum_credits;
    }

    public double getSum_avg_credits() {
        return sum_avg_credits;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }
}