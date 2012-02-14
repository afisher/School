import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class SimulationPanel extends JPanel implements MouseListener{
    private ArrayList<Pendulum> pendulums;

    public SimulationPanel() {
        super();
        pendulums = new ArrayList<Pendulum>();

        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pendulums.size() == 0) return;

        for (Pendulum p : pendulums) {
            p.paint(g);
        }
    }

    public void step() {
        for (Pendulum p : pendulums) {
            p.calcNewValues(pendulums);
        }

        for (Pendulum p : pendulums) {
            p.step(); // apply new values
        }
    }

    public void clear() {
        pendulums.clear(); 
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        double theta = Math.atan2(Params.yPivot - y, x - Params.xPivot);

        pendulums.add(new Pendulum(theta));
        repaint();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
