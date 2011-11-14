/*
 * SensorList.java
 *
 * @author jrl -- Created on April 9, 2007, 3:03 PM
 *
 */

package vehicles;

import java.util.ArrayList;

public class SensorList extends ArrayList<AbstractSensor> {    
    private static final boolean DEBUG=true;
    
    /** Creates a new instance of SensorList */
    public SensorList() {
        super();
    }
    
    
    public String toString() {
        String returnMe = "SensorList: ";
        
        returnMe += super.toString();
        
        return returnMe;
    }
}
