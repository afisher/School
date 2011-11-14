package vehicles;

/*
 * AbstractVehicle.java
 *
 * Created on September 10, 2003, 1:38 PM
 */

/**
 *
 * @author  wu_staff
 */
import ga.Evaluable;
import java.awt.*;
import java.awt.geom.Point2D;

/** The abstract class for Vehicles */
public abstract class AbstractVehicle {
    
    /** Where is the Vehicle */    
    protected Point2D.Double location;        
    /** Which direction is it pointed (in radians) */    
    protected double orientation;    
    /** Not used currently */    
    protected double velocity;
    
    /** The list of Sensors the vehicle has */    
    protected AbstractSensors mySensors;   
    /** Not used. */    
    protected Image myImage;
    /** How big is the vehicle; used for display and sensor location. */    
    protected int size=200;

    Evaluable ind;
    public void setInd(Evaluable i) {ind = i;}
    public Evaluable getInd() {return ind;}

    /** Creates a new instance of AbstractVehicle */
    public AbstractVehicle() {
        mySensors = new MySensors();
        size += (int) ((Math.random()-0.99)*size);
    }

    /** Creates a new instance of AbstractVehicle of a particular size*/
    public AbstractVehicle(int size) {
        mySensors = new MySensors();
        this.size = size;
    }

    /** Accessor
     * @return The image (not used)
     */    
    public java.awt.Image getImage() {
        return myImage;
    }
    /** Accessor
     * @return The orientation!
     */    
    public double getOrientation() {return orientation;}
    /** Accessor
     * @return the location!
     */    
    public Point2D.Double getLocation() {return location;}
    public double getX() {return location.getX();}
    public double getY() {return location.getY();}
    
    /** Accessor
     * @param nuOrientation The new orientation
     */    
    public void setOrientation(double nuOrientation) {orientation = nuOrientation;}
    /** Accessor
     * @param nuLocation The new location
     */    
    public void setLocation(Point2D.Double nuLocation) {location = nuLocation;}
    /** Adds a sensor to this vehicle
     * @param s The sensor to add
     */    
    public void addSensor(AbstractSensor s) {
        mySensors.addSensor(s);
    }
    
    /** Get sensor readings, calculate and return the resulting wheel drives.
     * @param theWorld To ask for sensor readings.
     * @return The output to the wheel motors.
     */    
    abstract public AbstractDriveOutput step(AbstractWorld theWorld);
    
    /** Paint this vehicle.  Just a circle, a line for the direction and 2 lines for the
     * sensors.
     * @param g the screen
     */    
    abstract public void paint(Graphics g);
    /** Accessor
     * @return The size
     */    
    public int getSize() {return size;}
}
