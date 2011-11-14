package vehicles;

/*
 * AbstractSource.java
 *
 * Created on September 11, 2003, 11:46 AM
 */

/**
 *
 * @author  levenick
 */
import java.awt.geom.*;
import java.awt.*;

public abstract class AbstractSource {
    
    protected Point2D.Double location;
    protected double intensity;
        
    public Point2D.Double getLocation(){return location;}
    public void setLocation (Point2D.Double l) {location = l;}
    
    public double getIntensity() {return intensity;}
    public void setIntensity(double i) {intensity = i;}
    abstract public void paint(Graphics g);
    
}
