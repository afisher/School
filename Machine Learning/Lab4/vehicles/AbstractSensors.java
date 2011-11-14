package vehicles;

/*
 * AbstractSensors.java
 *
 * Created on September 11, 2003, 4:09 PM
 */

/**
 *
 * @author  levenick
 */
import java.util.*;

public abstract class AbstractSensors {

    protected SensorList theSensors;
    
    /** Creates a new instance of Sensors */
    public AbstractSensors() {
        theSensors = new SensorList();
    }
    
    public void addSensor(AbstractSensor s) {
        theSensors.add(s); 
    }

}
