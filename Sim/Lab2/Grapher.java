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

    private int r = 5; // dot radius
    private int xTicks = 6; // number of ticks on the x-axis
    private int yTicks = 6; // number of ticks on the y-axis

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        calcSizes();

        g.drawLine(padding, height, width+padding, height);
        g.drawLine(padding, 0, padding, height);

        g.drawString("time", padding + width/2, height + padding - 5);
        g.drawString("pop", 5, height/2);

        int t = 0;
        int maxT = 0;

        //find the max val
        int maxH  = 0, maxP = 0;
        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            if (h > maxH) maxH = h;
            if (p > maxP) maxP = p;

            maxT++;
        }

        maxT--;

        int xInc;
        if (maxT > xTicks) xInc = maxT / xTicks;
        else xInc = 1;

        int yInc;
        if (maxH > yTicks) yInc = maxH / yTicks;
        else yInc = 1;

        // draw tick marks for y-axis
        int yPos = 0; // where we are on the y axis
        for (int i = 0; i < yTicks; i++) {
            int drawPos = height - (int)(yPos * (height / (double) maxH));
            if (yPos % yInc == 0) {
                g.drawLine(padding - 10, drawPos, padding, drawPos);
                g.drawString(""+yPos, padding - 50, drawPos+r);
            }
            yPos += yInc;
        }

        int prevX = 0, prevY1 = 0, prevY2 = 0;
        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            // calculate where to put the dots
            int x  = padding + (int)(t * (width  / (double) maxT)) - r;
            int y1 = height  - (int)(h * (height / (double) maxH))  - r;
            int y2 = height  - (int)(p * (height / (double) maxP))  - r;

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

            // draw tick marks for x-axis
            if (t % xInc == 0) {
                g.drawLine(x+r, height, x+r, height+10);
                g.drawString(""+t, x, height+30);
            }


            t++;
        }
    }
}
