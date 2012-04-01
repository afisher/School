import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;

public class RoadPanel extends JPanel {
    
    Vector theLanes;

    public RoadPanel(Vector l) {
        resize(View.WIDTH, View.HEIGHT);
        setVisible(true);

        theLanes = l;
    }

    public void paint(Graphics g) {
        Dimension d = getSize();

        int bottom = d.height - 50;
        int laneWidth = 10;

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);

        g.drawLine(0, bottom, d.width, bottom);
        for (int i = 0; i < theLanes.size(); i++) { // paint each lane
            g.setColor(Color.black);
            g.drawLine(0, bottom - (i + 1) * laneWidth, d.width, bottom - (i + 1) * laneWidth);
            ((Lane) theLanes.elementAt(i)).paint(g, bottom - (i + 1) * laneWidth + 2);
        }
    }
}
