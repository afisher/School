/*  Lane.java

A lane knows what road it's in and has an unordered list of vehicles that are in it.
It would make things much more efficient if the vehicles in a lane were kept in order; details!
There are two very inefficient methods (nearestVehicleAhead/Behind) to find out how much
space there is for merging.
Lanes also can add and remove a vehicle (when someone changes lanes) and paint itself, by
painting all the vehicles in this lane.

Author: JRL 10/98
 */

import java.awt.*;
import java.util.*;

public class Lane {

    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
    Road theRoad;
    int index;

    public int getIndex() {
        return index;
    }

    public Lane(Road r, int i) {
        index = i;
        theRoad = r;
    }

    public Road getRoad() {
        return theRoad;
    }

    public void addVehicle(Vehicle nuVehicle) {
        vehicles.add(nuVehicle);
        nuVehicle.setLane(this);
    }

    public void clear() {
        vehicles.clear();
    }

    public void removeVehicle(Vehicle oldVehicle) {
        vehicles.remove(oldVehicle);
    }

    int throughput() {
        int tp = 0;

        for (int i = vehicles.size() - 1; i >= 0; i--) {
            Vehicle theVehicle = (Vehicle) vehicles.get(i);
        }

        return tp;
    }

    public void moveVehicles() {
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            Vehicle theVehicle = (Vehicle) vehicles.get(i);
            theVehicle.move();
        }
    }

    public double nearestVehicleAhead(Vehicle thisVehicle) {
        double minDistance = 999999;
        double loc = thisVehicle.getLocation();

        for (int i = vehicles.size() - 1; i >= 0; i--) {
            Vehicle theVehicle = (Vehicle) vehicles.get(i);
            if (theVehicle != thisVehicle) {
                double d = theVehicle.getLocation() - loc;
                if (d >= 0 && d < minDistance) {
                    minDistance = d;
                }
            }
        }

        return minDistance;
    }

    public double nearestVehicleBehind(Vehicle thisVehicle) {
        double minDistance = 999999;
        double loc = thisVehicle.getLocation();

        for (int i = vehicles.size() - 1; i >= 0; i--) {
            Vehicle theVehicle = (Vehicle) vehicles.get(i);
            if (theVehicle != thisVehicle) {
                double d = loc - theVehicle.getLocation();
                if (d >= 0 && d < minDistance) {
                    minDistance = d;
                }
            }
        }

        return minDistance;
    }

    public void paint(Graphics g, int h) {
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            if (i < vehicles.size()) {
                Vehicle theVehicle = (Vehicle) vehicles.get(i);
                theVehicle.paint(g, h);
            }
        }
    }
}
