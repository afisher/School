import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Grapher extends JPanel {
    private ArrayList<DataPair> data;

    public Grapher(ArrayList<DataPair> d) {
        super();
        data = d;
    }

    public void setData(ArrayList<DataPair> d) {
        data = d;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = getHeight();
        int width  = getWidth();
        int padding = 100;

        int r= 5;

        g.drawLine(padding, height - padding, width, height - padding);
        g.drawLine(padding, 0, padding, height - padding);

        g.drawString("time", width/2, height - 5);
        g.drawString("pop", 5, height/2);

        int t = 0;
        int maxT = data.size();

        //find the max val
        int maxH = 0;
        int maxP = 0;
        for (DataPair pair : data) {
            int h = pair.getH();
            int p = pair.getP();

            if (h > maxH) maxH = h;
            if (p > maxP) maxP = p;
        }

        for (DataPair pair : data) {
            g.setColor(Color.BLUE);
            int h = pair.getH();

            int x = (int)(t * (width / (double) maxT));
            int y = (int)(h * (height / (double) maxH));

            g.fillOval(x/*+padding-r*/, y/*-padding-r*/, 2*r, 2*r);

            /*g.fillOval(width * t/maxT + padding-r, 
                       height - (height * h/maxH) + padding-r, 
                       2*r, 2*r);
            
            g.setColor(Color.RED);
            int p = pair.getP();

            g.fillOval(width * t/maxT + padding-r,
                       height - (height * p/maxP) + padding-r, 
                       2*r, 2*r);*/

            t++;
        }
    }
}
