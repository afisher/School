/* Driver.java

    A minimal abstract base class for Drivers. Since it is abstract, it cannot be instantiated.
    
    Author: JRL, 10/00
    Modified: JRL 11/10/00 to add safeDistance (from subclasses for uniformity)
*/

abstract public class Driver {
    static final int DECELERATE = -5;
    static final int ACCELERATE = 2;
    static final int NO_MORE_OFTEN=10;

    Vehicle myVehicle;
    double preferredSpeed;
    double getPreferredSpeed() {return preferredSpeed;}
    Lane preferredLane;

    Driver(Vehicle c, double p) {
        myVehicle = c;
        preferredSpeed = p;
    }

    void setPreferredLane() {
        preferredLane = null;  // none, and it will never be used, I hope!
    }
    
    public String toString () {
        return "Driver";
    }

    public abstract double speedAdjustment();
    public abstract Lane getNewLane();
    
    public double safeDistance (double speed) {
        return speed;     // Completely arbitrary
    }
    
}


