package vehicles;

/*
 * AbstractWorld.java
 *
 * Created on September 11, 2003, 4:04 PM
 * Modified to replace Vectors with VehicleList and SourceList 4/9/7, jrl
 */

/**
 *
 * @author  levenick
 */
import java.awt.*;
import java.awt.geom.Point2D;

/** The abstract world for the vehicles simulation.
    Includes the list of vehicles and the list of sources.
 */
public abstract class AbstractWorld {
    
    /**
     * List of vehicles
     */    
    protected VehicleList theVehicleList;
    /** The list of sources */    
    protected SourceList theSourceList;
    
    /**
     * Creates a new instance of World 
     */
    public AbstractWorld() {
        theVehicleList = new VehicleList();
        theSourceList = new SourceList();
    }
    
    /** Adds a vehicle
     * @param nuVehicle The new vehicle to add.
     */    
    abstract public void addVehicle (AbstractVehicle nuVehicle);    
    /**
     * Adds a source la la la!
     * @param nuSource The new source to add.
     */    
    abstract public void addSource (AbstractSource nuSource);
    /** Step the world one step.  To do this must step each vehicle. */    
    abstract public void step();
    /** Calculate the value of the stimulus over all the sources (note: to handle multiple sources, 
     * this should likely have another parameter to specify the type of source?).
     * @param where The location of the sensor that wants to know its current input.
     * @return The value of the stimulus (summed over all the sources) at the Point passed
     */    
    abstract public double getStimulusStrength(Point2D.Double where);
    /** Paint the world.  Need to paint all the Vehicles and Sources.
     * @param g The Graphics context (from the Applet).
     */    
    abstract public void paint(Graphics g);
}
