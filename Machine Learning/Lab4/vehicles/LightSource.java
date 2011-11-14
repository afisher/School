package vehicles;

/*
 * LightSource.java
 *
 * Created on September 11, 2003, 12:32 PM
 */

/**
 *
 * @author  levenick
 */
import java.awt.geom.Point2D;
import java.awt.*;

public class LightSource extends AbstractSource {
    int width=16;
    int offset = width/2;
    
    /** Creates a new instance of LightSource */
    public LightSource() {
    }
    
    public LightSource(Point2D.Double location, double strength) {
        setIntensity(strength);
        setLocation(location);
    }
    
    public void paint(Graphics g) {
        int x = (int)getLocation().getX();
        int y = (int)getLocation().getY();
        g.setColor(new Color(200, 150, 200));
        g.fillOval(x-offset, y-offset, width, width);
    }
    
}
