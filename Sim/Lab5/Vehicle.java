/* Vehicle.java

An abstract base class for Vehicles. Since it is abstract, it cannot be instantiated.
Other vehicles can be added by following the pattern in Car.

Author: JRL, 10/00
Modified for Phase 1 of Lab 7: 11/11/00
 */

import java.awt.*;

public abstract class Vehicle {

    Color color;
    double speed, location;
    Lane myLane;
    Driver myDriver;
    
    Driver getDriver() {
        return myDriver;
    }

    double getLocation() {
        return location;
    }

    double getSpeed() {
        return speed;
    }

    Lane getLane() {
        return myLane;
    }

    Vehicle() {
    }

    public Vehicle(int pos, Color c, double ps, int driverType) {
        location = pos;
        color = c;
        if (driverType == View.IDEE) {
            myDriver = new IdeeFixee(this, ps);
        } else if (driverType == View.MAX) {
            myDriver = new MaxHeadRoom(this, ps);
        } else if (driverType == View.OWN) {
            myDriver = new IOwnTheRoad(this, ps);
        } else if (driverType == View.AVE) {
            myDriver = new AverageDriver(this, ps);
        } else {
            myDriver = new CrazyDriver(this, ps);
        }
    }

    public void setPreferredLaneIf() {
        myDriver.setPreferredLane();
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setLane(Lane l) {
        myLane = l;
    }

    public Color getColor() {
        return color;
    }

    protected void moveForward() {
        location += speed / 10;
    }

    boolean through, fastEnough;
    int SLOP = 5;

    public void move() {
        fastEnough = myDriver.getPreferredSpeed() - speed < SLOP;
        through = false;
        //location += speed / 10;           // Um, so they go slowly enough?
        moveForward();
        if (location > RoadPanel.width) {    // A loop!
            location = 0;
            through = true;
        }

        speed += myDriver.speedAdjustment();        // All decisions are made by the driver.
        if (speed < 0) {
            speed = 0;  // No backing up!
        }
        Lane otherLane = myDriver.getNewLane();     // Change lanes?

        if (otherLane != null) {  // i.e. if they want to switch lanes
            myLane.removeVehicle(this);
            otherLane.addVehicle(this);
            myLane = otherLane;
        }
    }

    public int getDisplayX() {
        return (int) Math.rint(location);
    }

    public abstract void paint(Graphics g, int y);      // leave it to the individual vehicles.
}
