import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class PhaseGrapher extends JPanel {
    private ArrayList<DataPair> data;

    private final int padding = 100; // axes padding
    private int height;
    private int width;

    private int maxH;
    private int maxP;

    private int r = 3; // dot radius
    private int xTicks = 6; // number of ticks on the x-axis
    private int yTicks = 6; // number of ticks on the y-axis

    private int hInc; // amount to increment h for y-axis ticks
    private int pInc; // amount to increment p for y-axis ticks

    public PhaseGrapher(ArrayList<DataPair> d) {
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

        g.drawString("predators", padding + width/2, height + padding - 5);
        g.drawString("prey", 5, height/2);
    }

    private void calcMaxes() {
        maxH = -1;
        maxP = -1;

        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            if (h > maxH) maxH = h;
            if (p > maxP) maxP = p;
        }
    }

    private void calcTickIncrements() {
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
        int pPos = 0;
        for (int i = 0; i < xTicks; i++) {
            int drawPos = xVal(pPos, maxP) + r;

            if (pPos % pInc == 0) {

                g.drawLine(drawPos, height, drawPos, height + 10);
                g.drawString("" + pPos, drawPos, height + 30);
            }

            pPos += pInc;
        }
    }

    private void drawYTicks(Graphics g) {
        int hPos = 0;
        for (int i = 0; i < yTicks; i++) {
            int drawPos = yVal(hPos, maxH) + r;

            if (hPos % hInc == 0) {

                // draw line
                g.drawLine(padding - 10, drawPos, padding, drawPos);

                // draw tick values for herbivores
                g.drawString("" + hPos, padding - 50, drawPos + r);
            }

            hPos += hInc;
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

        int prevX = 0, prevY = 0;

        for (DataPair pair : data) {

            int h = pair.getH();
            int p = pair.getP();

            // calculate where to put the dot
            int x  = xVal(p, maxP);
            int y  = yVal(h, maxH);

            // draw dot
            g.fillOval(x, y, 2*r, 2*r);

            // draw a line connecting this dot to the last
            if (prevX != 0) {
                g.drawLine(prevX+r, prevY+r, x+r, y+r); 
            }

            // remember this dot
            prevX = x;
            prevY = y;

        }
    }
}
