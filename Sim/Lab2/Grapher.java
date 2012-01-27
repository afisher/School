import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Grapher extends JPanel {
    private ArrayList<DataPair> data;

    //final int padding = 100; // axes padding
    //int height;
    //int width;

    int r= 5; // dot radius

    public Grapher(ArrayList<DataPair> d) {
        super();
        data = d;
    }

    public void setData(ArrayList<DataPair> d) {
        data = d;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int padding = 100;
        int height = getHeight() - padding;
        int width  = getWidth()  - padding;

        g.drawLine(padding, height, width+padding, height);
        g.drawLine(padding, 0, padding, height);

        g.drawString("time", padding + width/2, height + padding - 5);
        g.drawString("pop", 5, height/2);

        int t = 0;
        int maxT = 0;

        //find the max val
        int max  = 0;
        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            System.out.println(p);

            if (h > max) max = h;
            if (p > max) max = p;

            maxT++;
        }

        maxT--;

        // draw tick marks
        int xTick = width  / 5;
        int yTick = height / 5;
        for (int i = 0; i < maxT; i++) {
            g.drawLine(padding+i*xTick, height, padding+i*xTick, height+5); 
            g.drawString(""+i, padding+i*xTick-5, height+50);
        }
        for (int i = 0; i < height; i += yTick) {
            g.drawLine(padding-5, i, padding, i); 
        }

        int prevX = 0, prevY1 = 0, prevY2 = 0;
        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            // calculate where to put the dots
            int x  = padding + (int)(t * (width  / (double) maxT)) - r;
            int y1 = height - (int)(h * (height / (double) max)) - r;
            int y2 = height - (int)(p * (height / (double) max)) - r;

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

            t++;
        }
    }
}
