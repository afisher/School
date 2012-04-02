/*  Controller.java

The center of a prototype traffic simulation.
Shows how to thread an ordinary Applet so that animation works.
Once an Applet is created, it is sent init() and then start().
Init creates the Model and View (which extends Panel).
Start, confusingly, recreates the Applet as a Thread, and starts it;
realize that t.start() is not a recursive call, but rather the method
that kicks off the new Thread.

11/00
run() modified to run 8 experiments, gather stats, and report
boolean batch added to turn off repaint and sleep

Author: JRL 10/98, modified 11/00
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Controller {

    Thread t;
    View theView;
    Model theModel;
    static int numLanes = 3;
    Experimenter myCreator;
    boolean running = false;
    static int delay = 40;

    public Controller(Experimenter e) { // remember who created me
        myCreator = e;
    }

    public void init() {
        theModel = new Model(this, numLanes);
        theView = new View(theModel);
    }

    public void run() {
        new Thread() {
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(delay);       // delay if nto running batch
                        theModel.moveVehicles();
                        theView.repaint();
                    } catch (Exception e) {
                        System.out.println("oops!" + e);
                    }
                }
            }
        }.start();
    }

    public void setRunning(boolean r) { running = r; }

    public int numCars() {
        return theView.numCars(); 
    }
}
