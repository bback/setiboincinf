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

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

import boincinf.netstat.*;
import boincinf.netstat.SQLStorage.*;

/**
 * @author kgraul
 */
public class DiagramPainter {

    private static java.util.List<SQLStorage.CreditsAtTime> creditsAtTimeList = null; // store.getCreditAtTimeForDayCount(90);
    private static double avg14_m = -1.0;
    private static double avg30_m = -1.0;

    /**
     * called to draw the stats canvas.
     */
    public static void canvasPaintControl(final PaintEvent e, final SQLStorage store) {

        final Canvas canvas = (Canvas) e.widget;
        final int maxX = canvas.getSize().x;
        final int maxY = canvas.getSize().y;

        // clear canvas
        e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WHITE));
        e.gc.fillRectangle(0,0,maxX,maxY);

        if( getCreditsAtTimeList() == null || getCreditsAtTimeList().size() < 2 ) {
            return;
        }

        final java.util.List<SQLStorage.CreditsAtTime> cats = getCreditsAtTimeList();
        final SQLStorage.CreditsAtTime first = cats.get(0);
        final SQLStorage.CreditsAtTime last = cats.get(cats.size()-1);

        final long divFactor = 1000; // ms -> sec

        final long deltaTime = (last.timestamp.getTime() - first.timestamp.getTime())/divFactor;
        final long minTime = first.timestamp.getTime()/divFactor;
//        long maxTime = last.timestamp.getTime()/divFactor;

        final long deltaCredits = (long)(last.sum_credits - first.sum_credits);
        final long minCredits = (long)first.sum_credits;
//        long maxCredits = (long)last.sum_credits;

        final int axisPos = 10;

        final long canvasRangeX = maxX - axisPos - 10; // keep 10 pixel of space left and right
        final long canvasRangeY = maxY - axisPos - 10;

        // Set the line color and draw axis
        e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
        e.gc.drawLine(axisPos, maxY-axisPos, maxX-axisPos, maxY-axisPos);
        e.gc.drawLine(axisPos, maxY-axisPos, axisPos, axisPos);

        e.gc.setLineWidth(2);

        // Faktor um credits/time auf die verfügbare range umzurechnen
        final double factorX = ((double)(canvasRangeX) / (double)deltaTime);
        final double factorY = ((double)(canvasRangeY) / (double)deltaCredits);

        final long starttime_14 = getStartTime(14);
        final long starttime_30 = getStartTime(30);
        SQLStorage.CreditsAtTime first_avg14 = null;
        SQLStorage.CreditsAtTime first_avg30 = null;

        // find start avg14 and avg30
        for( final CreditsAtTime cat : cats ) {
            if( first_avg14 == null && cat.timestamp.getTime() > starttime_14 ) {
                first_avg14 = cat;
            }
            if( first_avg30 == null && cat.timestamp.getTime() > starttime_30 ) {
                first_avg30 = cat;
            }
        }

        // zeichne gerade von anfang bis ende der werte (Gesamtdurchschnitt)
        {
            final long x1 = (long)((first.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y1 = (long)((first.sum_credits - minCredits) * factorY) + axisPos;
            y1 = maxY - y1;

            final long x2 = (long)((last.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y2 = (long)((last.sum_credits - minCredits) * factorY) + axisPos;
            y2 = maxY - y2;

            e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GREEN));
            e.gc.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }

        // zeichne gerade von beginn avg14 bis ende
        if( first_avg14 != null ) {
            final long x1 = (long)((first_avg14.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y1 = (long)((first_avg14.sum_credits - minCredits) * factorY) + axisPos;
            y1 = maxY - y1;

            final long x2 = (long)((last.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y2 = (long)((last.sum_credits - minCredits) * factorY) + axisPos;
            y2 = maxY - y2;

            e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
            e.gc.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }

        if( first_avg30 != null ) {
            final long x1 = (long)((first_avg30.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y1 = (long)((first_avg30.sum_credits - minCredits) * factorY) + axisPos;
            y1 = maxY - y1;

            final long x2 = (long)((last.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long y2 = (long)((last.sum_credits - minCredits) * factorY) + axisPos;
            y2 = maxY - y2;

            e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_MAGENTA));
            e.gc.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }

        // messwerte einzeichnen

        e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
        long lastX = -1;
        long lastY = -1;
        for( final CreditsAtTime creditsAtTime : cats ) {
            final SQLStorage.CreditsAtTime cat = creditsAtTime;
            final long posX = (long)((cat.timestamp.getTime()/divFactor - minTime) * factorX) + axisPos;
            long posY = (long)((cat.sum_credits - minCredits) * factorY) + axisPos;
            posY = maxY - posY;

            if( lastX > 0 ) {
                e.gc.drawLine((int)lastX, (int)lastY, (int)posX, (int)posY);
            }
            lastX = posX;
            lastY = posY;
        }

        // TODO: wertebereich einzeichnen
    }

    private static long getStartTime(final int daycount) {
        // TODO: we start by NOW - daycount, maybe we should ignore the time and select only by date
        // (by setting startts to begin of day)
        if( daycount > 0 ) {
            final long now = System.currentTimeMillis();
            final long diff = (daycount * 24l * 60l * 60l * 1000l);
            final long starttime = now - diff;
            return starttime;
        } else {
            return 0;
        }
    }

    public static java.util.List<SQLStorage.CreditsAtTime> getCreditsAtTimeList() {
        return creditsAtTimeList;
    }
    public static void setCreditsAtTimeList(final java.util.List<SQLStorage.CreditsAtTime> catList) {
        creditsAtTimeList = catList;
    }
    public static double getAvg14_m() {
        return avg14_m;
    }
    public static void setAvg14_m(final String s) {
        double d = -1.0;
        try { d = Double.parseDouble(s); } catch(final Exception ex) { }
        DiagramPainter.avg14_m = d;
    }
    public static double getAvg30_m() {
        return avg30_m;
    }
    public static void setAvg30_m(final String s) {
        double d = -1.0;
        try { d = Double.parseDouble(s); } catch(final Exception ex) { }
        DiagramPainter.avg30_m = d;
    }
}
