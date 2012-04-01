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
import java.awt.image.BufferStrategy;

public class Road extends JFrame {

    int count = 0;
    Controller a;
    int numLanes;
    Vector theLanes;

    Controller getController() {
        return a;
    }

    Vector getLanes() {
        return theLanes;
    }

    public Road(Controller theCtrllr, int nLanes) {
        a = theCtrllr;
        numLanes = nLanes;
        theLanes = new Vector();
        for (int i = 1; i <= numLanes; i++) {
            System.out.print("Adding a lane! i=" + i);
            theLanes.addElement(new Lane(this, i - 1));
        }
        resize(View.WIDTH, View.HEIGHT);
        setVisible(true);
        createBufferStrategy(2);
    }

    public Road(Controller theCtrllr, Vehicle aVehicle, int nLanes) {
        this(theCtrllr, nLanes);
        ((Lane) theLanes.elementAt(0)).addVehicle(aVehicle);
    }

    public Lane getNextLaneRight(Lane thisLane) {
        if (thisLane.getIndex() == 0) {
            return null;
        }
        return (Lane) theLanes.elementAt(thisLane.getIndex() - 1);
    }

    public Lane getNextLaneLeft(Lane thisLane) {
        if (thisLane.getIndex() == theLanes.size() - 1) {
            return null;
        }
        return (Lane) theLanes.elementAt(thisLane.getIndex() + 1);
    }

    public void addVehicle(Vehicle nuVehicle) {
        ((Lane) theLanes.elementAt(0)).addVehicle(nuVehicle);
    }

    public void addVehicle(Vehicle nuVehicle, int whichLane) {
        if (whichLane < theLanes.size()) {
            ((Lane) theLanes.elementAt(whichLane)).addVehicle(nuVehicle);
        }
    }
    boolean firstTime = true;
    Image offScreenImage;
    Graphics otherG;

    boolean noCars=false;
    public void paint(Graphics g) {
        Dimension d = getSize();

        // Double buffering.  
        /*boolean offScreening = true; // demo, set to false to see without
        if (offScreening) {
            if (firstTime) {  // only create the image once
                offScreenImage = a.createImage(d.width, d.height);
                otherG = offScreenImage.getGraphics();
                firstTime = false;
            }

            doTheDrawing(otherG, d);
            g.drawImage(offScreenImage, 0, 0, this);
        } else {*/
            doTheDrawing(g, d);
        //}
    }

    String throughput() {
        int tp=0;
        for (int i = 0; i < theLanes.size(); i++) { // paint each lane
            tp += ((Lane) theLanes.elementAt(i)).throughput();
        }
        return "" + tp;
    }

    String aveSpeed() {
        return "" + 0;
    }

    String stats() {
        return "Ave speed (last xx steps)=" + aveSpeed() + " throughput(xx steps)'" + throughput();
    }

    void doTheDrawing(Graphics g, Dimension d) {
        int bottom = d.height - 50;
        int laneWidth = 10;

        g.setColor(Color.white);
        g.fillRect(0, 100, d.width, d.height);
        g.setColor(Color.black);

        g.drawLine(0, bottom, d.width, bottom);
        for (int i = 0; i < theLanes.size(); i++) { // paint each lane
            g.setColor(Color.black);
            g.drawLine(0, bottom - (i + 1) * laneWidth, d.width, bottom - (i + 1) * laneWidth);
            ((Lane) theLanes.elementAt(i)).paint(g, bottom - (i + 1) * laneWidth + 2);
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void moveVehicles() {
        for (int i = 0; i < theLanes.size(); i++) {
            ((Lane) theLanes.elementAt(i)).moveVehicles();
        }
    }

    public void clear() {
        for (int i = 0; i < theLanes.size(); i++) {
            ((Lane) theLanes.elementAt(i)).clear();
        }
    }
}
