/* IOwnTheRoad.java

    Stays in current lane unless not going fast enough.
    
    Author: JRL, 11/11/00
*/
import java.awt.*;

class IOwnTheRoad extends Driver {
    
    int timeUntilCanChangeLanes;            // to prevent cars from jittering back and forth
    
    IOwnTheRoad(String n, Vehicle c, double p) {
        super(n, c, p);
        myVehicle.setColor(Color.black);
    }

    public String toString () {
        return "Average Driver=" + name;
    }

    public double speedAdjustment() {
        double adjust=0;
        double speed = myVehicle.getSpeed();
        Lane myLane = myVehicle.getLane();
        double nca = myLane.nearestVehicleAhead(myVehicle);

        if ( nca < safeDistance(speed)) { // if too close to the car in front
            adjust = DECELERATE;   // always the same deceleration
        }
        else {
            if (speed < preferredSpeed) {
                adjust = ACCELERATE; // always the same acceleration
            }
        }
        return adjust;
    }

    public Lane getNewLane() {
        Lane returnMe=null;
        
        double speed = myVehicle.getSpeed();
        timeUntilCanChangeLanes--;
        if (timeUntilCanChangeLanes > 0)
            return null;  // i.e. not change lanes now
        
        Lane myLane = myVehicle.getLane();
        Lane nextRight = myLane.getRoad().getNextLaneRight(myLane);
        Lane nextLeft = myLane.getRoad().getNextLaneLeft(myLane);

        double nca = myLane.nearestVehicleAhead(myVehicle);
        double closestSafeDistance = safeDistance(speed);

        double ncaR=0, ncaL=0, ncbL=0, ncbR=0;
        boolean canGoL=false, canGoR=false;     
        if (nextLeft != null) {         // move left to pass
            ncaL = nextLeft.nearestVehicleAhead(myVehicle);
            ncbL = nextLeft.nearestVehicleBehind(myVehicle);
            canGoL = canGo(ncaL, ncbL, closestSafeDistance);
        }
        if (nextRight != null) {        // move right if I can
            ncaR = nextRight.nearestVehicleAhead(myVehicle);
            ncbR = nextRight.nearestVehicleBehind(myVehicle);
            canGoR = canGo(ncaR, ncbR, closestSafeDistance);
        }
        
        // the variable are all set, so now decide what to do
        if (speed < preferredSpeed-5) {
            if (canGoR) {
                returnMe = nextRight;
            }
            else if (canGoL)
                returnMe = nextLeft;
        }
        
        if (returnMe != null) {
            timeUntilCanChangeLanes = NO_MORE_OFTEN;
        }

        return returnMe;
    }

    boolean canGo(double nca, double ncb, double least) { // safe to switch Ahead and Behind?
        return nca > least && ncb > least;
    }
}
