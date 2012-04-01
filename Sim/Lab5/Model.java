/*  Model.java

Creates a Road and two cars to start.
Other functionality is just passed through to the Road.

Author: JRL 10/98
Modified: JRL 11/10/00 to catch exceptions - an improvement over discarding them!
 */

import java.awt.*;

public class Model {

    Controller theController;
    Road theRoad;
    int time;

    Model(Controller theContr, int numLanes) {
        theRoad = new Road(theContr, numLanes);
        theController = theContr;
    }

    public void init() {
        int initSpeed = 44;          // used to give different desired speeds to the cars

        theRoad.clear();
        theRoad.repaint();

        /*for (int i = 0; i < theController.numCars(); i++) {
            Car newCar = new Car(randLoc(), randColor(), initSpeed);
            addVehicle(newCar);
            newCar.setPreferredLaneIf();  // if idee fixee
            initSpeed += rand(7);
            if (initSpeed > 81) {
                initSpeed = 43;
            }
        }*/

    }

    int rand(int max) {
        return (int) (Math.random() * max);
    }

    /*public void paint(Graphics g) {
        theRoad.paint(g);
    }*/

    public void moveVehicles() {
        try {
            theRoad.moveVehicles();
            theRoad.repaint();
            time++;
            /*if (time % 1000 == 0) {
                theController.theView.theTA.append("t=" + time + "\n");
            }*/
        } catch (Exception e) {
            //theController.theView.theTA.append("Oops! Exception! " + e);
            try {
                Thread.sleep(1000);
            } catch (Exception e2) {
            }
        }
    }

    public void addVehicle(Vehicle nuVehicle) {
        theRoad.addVehicle(nuVehicle, 0);
    }

    Color randColor() {
        return new Color(getRand(256), getRand(256), getRand(256));
    }

    int randLoc() {
        return (int) (Math.random() * View.WIDTH);
    }

    int getRand(int max) {
        return (int) (Math.random() * max);
    }
}
