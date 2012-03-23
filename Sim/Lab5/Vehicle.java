/* Vehicle.java

An abstract base class for Vehicles. Since it is abstract, it cannot be instantiated.
Other vehicles can be added by following the pattern in Car.

Author: JRL, 10/00
Modified for Phase 1 of Lab 7: 11/11/00
 */

import java.awt.*;

public abstract class Vehicle {

    Color color;
    int size;
    double speed, location;
    Lane myLane;
    Driver myDriver;
    Statistics stats;
    
    int getThrough() {
        return stats.throughput;
    }

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

    public Vehicle(int pos, Color c, double ps, String name, Statistics s) {
        stats = s;
        location = pos;
        color = c;
        int driverType = stats.getDriverType();
        if (driverType == View.IDEE) {
            myDriver = new IdeeFixee(name, this, ps);
        } else if (driverType == View.MAX) {
            myDriver = new MaxHeadRoom(name, this, ps);
        } else if (driverType == View.OWN) {
            myDriver = new IOwnTheRoad(name, this, ps);
        } else {
            myDriver = new AverageDriver(name, this, ps);
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

    void updateStats() {
        if (through) {
            stats.incThrough();
        }
        if (fastEnough) {
            stats.incFastEnough();
        }
    }
    boolean through, fastEnough;
    int SLOP = 5;

    public void move() {
        fastEnough = myDriver.getPreferredSpeed() - speed < SLOP;
        through = false;
        location += speed / 10;           // Um, so they go slowly enough?
        if (location > View.WIDTH) {    // A loop!
            location = 0;
            through = true;
        }

        updateStats();

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
