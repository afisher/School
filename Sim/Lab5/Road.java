/*  Road.java

The road is the main thing in the model; it is composed of a number of lanes.
Road has methods to return the next lane to the right or left, given a particular lane --
this because lanes don't know anything except what cars are in them.
Illustrates the use of double buffering to reduce visual chatter on updates.
Extending Component makes it have a public void paint(Graphics) method to override.
Notice that the paint methods sent to Lanes have two parameters instead of one.

Author: JRL 10/98
Modified: JRL 11/10/00 to only create the offScreen image once.
 */

import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;

public class Road extends JFrame {

    int count = 0;
    Controller a;
    ArrayList<Lane> theLanes;
    RoadPanel myPanel;

    Controller getController() {
        return a;
    }

    ArrayList getLanes() {
        return theLanes;
    }

    public Road(Controller theCtrllr, int nLanes) {
        a = theCtrllr;
        theLanes = new ArrayList<Lane>();
        for (int i = 1; i <= nLanes; i++) {
            System.out.print("Adding a lane! i=" + i);
            theLanes.add(new Lane(this, i - 1));
        }
        resize(View.WIDTH, View.HEIGHT);

        myPanel = new RoadPanel(theLanes);
        JScrollPane scroll = new JScrollPane(myPanel);

        add(scroll);

        setVisible(true);
    }

    public Road(Controller theCtrllr, Vehicle aVehicle, int nLanes) {
        this(theCtrllr, nLanes);
        ((Lane) theLanes.get(0)).addVehicle(aVehicle);
    }

    public Lane getNextLaneRight(Lane thisLane) {
        if (thisLane.getIndex() == 0) {
            return null;
        }
        return (Lane) theLanes.get(thisLane.getIndex() - 1);
    }

    public Lane getNextLaneLeft(Lane thisLane) {
        if (thisLane.getIndex() == theLanes.size() - 1) {
            return null;
        }
        return (Lane) theLanes.get(thisLane.getIndex() + 1);
    }

    public void addVehicle(Vehicle nuVehicle) {
        ((Lane) theLanes.get(0)).addVehicle(nuVehicle);
    }

    public void addVehicle(Vehicle nuVehicle, int whichLane) {
        if (whichLane < theLanes.size()) {
            ((Lane) theLanes.get(whichLane)).addVehicle(nuVehicle);
        }
    }
    boolean firstTime = true;
    Image offScreenImage;
    Graphics otherG;

    boolean noCars=false;
    public void paint(Graphics g) {
        myPanel.repaint();
    }

    String throughput() {
        int tp=0;
        for (int i = 0; i < theLanes.size(); i++) { // paint each lane
            tp += ((Lane) theLanes.get(i)).throughput();
        }
        return "" + tp;
    }

    String aveSpeed() {
        return "" + 0;
    }

    String stats() {
        return "Ave speed (last xx steps)=" + aveSpeed() + " throughput(xx steps)'" + throughput();
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void moveVehicles() {
        for (int i = 0; i < theLanes.size(); i++) {
            ((Lane) theLanes.get(i)).moveVehicles();
        }
    }

    public void clear() {
        for (int i = 0; i < theLanes.size(); i++) {
            ((Lane) theLanes.get(i)).clear();
        }
    }

    public void addLane() {
        theLanes.add(new Lane(this, theLanes.size()));
        repaint();
    }

    public void removeLane() {
        if (theLanes.size() > 0) {
            theLanes.remove(theLanes.size() - 1);
            repaint();
        }
    }
}
