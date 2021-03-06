import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Grapher extends JPanel {
    private ArrayList<DataPair> data;

    private final int padding = 100; // axes padding
    private int height;
    private int width;

    private int maxH;
    private int maxP;
    private int maxT;

    private int r = 3; // dot radius
    private int xTicks = 6; // number of ticks on the x-axis
    private int yTicks = 6; // number of ticks on the y-axis

    private int tInc; // amount to increment t for x-axis ticks
    private int hInc; // amount to increment h for y-axis ticks
    private int pInc; // amount to increment p for y-axis ticks

    public Grapher(ArrayList<DataPair> d) {
        super();
        data = d;
    }

    public void setData(ArrayList<DataPair> d) {
        data = d;
    }

    private void calcSizes() {
        height = getHeight() - padding;
        width  = getWidth()  - padding;
    }

    private void drawAxes(Graphics g) {
        g.drawLine(padding, height, width+padding, height);
        g.drawLine(padding, 0, padding, height);

        g.drawString("time", padding + width/2, height + padding - 5);
        g.drawString("pop", 5, height/2);
    }

    private void calcMaxes() {
        maxH = -1;
        maxP = -1;
        maxT = -1;

        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            if (h > maxH) maxH = h;
            if (p > maxP) maxP = p;

            maxT++;
        }
    }

    private void calcTickIncrements() {
        if (maxT > xTicks) tInc = maxT / xTicks;
        else tInc = 1;

        if (maxH > yTicks) hInc = maxH / yTicks;
        else hInc = 1;

        if (maxP > yTicks) pInc = maxP / yTicks;
        else pInc = 1;
    }

    private int xVal(int value, int max) {
        return padding + (int)(value * (width / (double) max)) - r;
    }

    private int yVal(int value, int max) {
        return height - (int)(value * (height / (double) max)) - r;
    }

    private void drawXTicks(Graphics g) {
        int tPos = 0;
        for (int i = 0; i < xTicks; i++) {
            int drawPos = xVal(tPos, maxT) + r;

            if (tPos % tInc == 0) {
                g.setColor(Color.BLACK);

                g.drawLine(drawPos, height, drawPos, height + 10);
                g.drawString("" + tPos, drawPos, height + 30);
            }

            tPos += tInc;
        }
    }

    private void drawYTicks(Graphics g) {
        int hPos = 0;
        int pPos = 0;
        for (int i = 0; i < yTicks; i++) {
            int drawPos = yVal(hPos, maxH) + r;

            if (hPos % hInc == 0) {

                // draw line
                g.setColor(Color.BLACK);
                g.drawLine(padding - 10, drawPos, padding, drawPos);

                // draw tick values for herbivores
                g.setColor(Color.BLUE);
                g.drawString("" + hPos, padding - 50, drawPos + r);

                // draw tick values for predators
                g.setColor(Color.RED);
                g.drawString("" + pPos, padding - 50, drawPos + r + 20);
            }

            hPos += hInc;
            pPos += pInc;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        calcSizes();
        drawAxes(g);
        calcMaxes();
        calcTickIncrements();
        drawXTicks(g);
        drawYTicks(g);

        int t = 0;
        int prevX = 0, prevY1 = 0, prevY2 = 0;

        for (DataPair pair : data) {

            int h = pair.getH();
            int p = pair.getP();

            // calculate where to put the dots
            int x  = xVal(t, maxT);
            int y1 = yVal(h, maxH);
            int y2 = yVal(p, maxP);

            // draw herbivore dot
            g.setColor(Color.BLUE);
            g.fillOval(x, y1, 2*r, 2*r);

            // draw a line connecting this dot to the last
            if (prevX != 0) {
                g.drawLine(prevX+r, prevY1+r, x+r, y1+r); 
            }

            // draw predator dot
            g.setColor(Color.RED);
            g.fillOval(x, y2, 2*r, 2*r);

            // draw a line connecting this dot to the last
            if (prevX != 0) {
                g.drawLine(prevX+r, prevY2+r, x+r, y2+r);
            }

            // remember these dots
            prevX  = x;
            prevY1 = y1;
            prevY2 = y2;

            g.setColor(Color.BLACK);

            t++;
        }
    }
}
