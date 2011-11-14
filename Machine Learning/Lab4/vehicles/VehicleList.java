/*
 * VehicleList.java
 *
 * @author jrl -- Created on April 9, 2007, 2:54 PM
 *
 */

package vehicles;

import java.util.ArrayList;

public class VehicleList extends ArrayList<AbstractVehicle>{
    
    private static final boolean DEBUG=true;
    
    /** Creates a new instance of VehicleList */
    public VehicleList() {
        super();
    }
    
    
    public String toString() {
        String returnMe = "VehicleList: ";
        
        returnMe += super.toString();
        
        return returnMe;
    }
}
