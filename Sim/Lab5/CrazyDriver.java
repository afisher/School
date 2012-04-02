// switches lanes whenever it can
import java.awt.*;

public class CrazyDriver extends Driver {
    
    int timeUntilCanChangeLanes; // to prevent cars from jittering back and forth
    
    CrazyDriver(Vehicle c, double p) {
        super(c, p);
        myVehicle.setColor(Color.pink);
    }

    public String toString () {
        return "Crazy Driver";
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
            canGoL = canGo(ncaL, ncbL, closestSafeDistance)
                      || nextLeft.vehicles.isEmpty();
        }
        if (nextRight != null) {        // move right if I can
            ncaR = nextRight.nearestVehicleAhead(myVehicle);
            ncbR = nextRight.nearestVehicleBehind(myVehicle);
            canGoR = canGo(ncaR, ncbR, closestSafeDistance)
                      || nextRight.vehicles.isEmpty();
        }

        // the variable are all set, so now decide what to do
        if (canGoL) {
            returnMe = nextLeft;
        }
        else if (canGoR) {
            returnMe = nextRight;
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
