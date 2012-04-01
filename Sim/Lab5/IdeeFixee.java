/* IdeeFixee.java
    
    Tries to stay in the lane I like the best; and go back there.
    
    Author: JRL, 11/11/00
*/
import java.awt.*;
import java.util.*;

class IdeeFixee extends Driver {
    
    int timeUntilCanChangeLanes;            // to prevent cars from jittering back and forth
    Lane myPreferredLane;
    
    IdeeFixee(Vehicle c, double p) {
        super(c, p);
        myVehicle.setColor(Color.red);
    }

    void setPreferredLane() {
        int i = (int) (Math.random()*Controller.numLanes);
        myPreferredLane = (Lane) (myVehicle.getLane().getRoad().getLanes().elementAt(i)); 
//        if (i==0) {
//            myVehicle.setColor(Color.blue);
//        } else if (i==1) {
//            myVehicle.setColor(Color.black);
//        } else if (i==2) {
//            myVehicle.setColor(Color.red);
//        } else if (i==3) {
//            myVehicle.setColor(Color.green);
//        } else myVehicle.setColor(Color.cyan);
    }
    
    public String toString () {
        return "IdeeFixee Driver";
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

    int SLACK = 10;      // how much we'll slow down without switching
    public Lane getNewLane() {
        Lane returnMe = null;
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
        
        Lane towardsPreferredLane=null, awayFromPreferredLane=null;
        if (myLane != myPreferredLane) {
            if (myPreferredLane.getIndex() < myLane.getIndex()) {
                towardsPreferredLane = nextRight;
                awayFromPreferredLane = nextLeft;
            }
        }
        if (towardsPreferredLane != null) {         // move left to pass
            ncaL = towardsPreferredLane.nearestVehicleAhead(myVehicle);
            ncbL = towardsPreferredLane.nearestVehicleBehind(myVehicle);
            canGoL = canGo(ncaL, ncbL, closestSafeDistance);
        } else if (nextLeft != null) {         // move left to pass
            ncaL = nextLeft.nearestVehicleAhead(myVehicle);
            ncbL = nextLeft.nearestVehicleBehind(myVehicle);
            canGoL = canGo(ncaL, ncbL, closestSafeDistance);
        }
        if (awayFromPreferredLane != null) {        // move right if I can
            ncaR = awayFromPreferredLane.nearestVehicleAhead(myVehicle);
            ncbR = awayFromPreferredLane.nearestVehicleBehind(myVehicle);
            canGoR = canGo(ncaR, ncbR, closestSafeDistance);
        } else if (nextRight != null) {        // move right if I can
            ncaR = nextRight.nearestVehicleAhead(myVehicle);
            ncbR = nextRight.nearestVehicleBehind(myVehicle);
            canGoR = canGo(ncaR, ncbR, closestSafeDistance);
        }
        
        // the variable are all set, so now decide what to do
        if (myLane == myPreferredLane && speed > preferredSpeed - SLACK) {
            returnMe = null;
        }
        else if (myLane == myPreferredLane) {
            if (canGoL) {
                returnMe = nextLeft;
            }
            else if(canGoR) {
                returnMe = nextRight;
            }
        } else if (nextRight == towardsPreferredLane && canGoR)
            returnMe = nextRight;
          else if (canGoL)
            returnMe = nextLeft;
        
        if (returnMe != null) {
            timeUntilCanChangeLanes = NO_MORE_OFTEN;
        }

        return returnMe;
    }

    boolean canGo(double nca, double ncb, double least) { // safe to switch Ahead and Behind?
        return nca > least && ncb > least;
    }
}
