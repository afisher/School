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

public class Controller extends Container implements Runnable {

    Thread t;
    View theView;
    Model theModel;
    static int numLanes = 3;
    Experimenter myCreator;
    int timeToRun = 1000;
    boolean batch = false;

    public Controller(Experimenter e) { // remember who created me
        myCreator = e;
    }

    public void init() {
        theModel = new Model(this, numLanes);
        theView = new View(theModel);
        setLayout(new BorderLayout());
        setSize(View.WIDTH, View.HEIGHT);
        setVisible(true);
        add(theView);

    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
        }
        t.start();
    }
    Vector stats = new Vector();

    public void run() {
        if (!batch) {
            for (int i = 0; i < timeToRun; i++) {
                try {
                    Thread.sleep(40);       // delay if nto running batch
                    theModel.moveVehicles();
                } catch (Exception e) {
                    System.out.println("oops!" + e);
                }
                theView.repaint();
            } // time for
        } else {
            for (int type = View.IDEE; type <= View.MAX; type++) {
                for (int n = 10; n <= Statistics.MAX_VEHICLES; n += 20) {
                    Statistics s = new Statistics(n, type);
                    theModel.init(s);
                    stats.addElement(s);
                    for (int i = 0; i < timeToRun; i++) {
                        theModel.moveVehicles();
                    } // time for
                    System.out.print(".");              // (very) primitive progress bar
                } // n for
            } // driver for
            //myCreator.finishedSir(stats);   // tell the experimenter that we're done (and hand off the stats)
        }
    }

    public void stop() { // good practice to stop Threads when we stop
        if (t != null) {
            t.stop();
        }
    }
}
