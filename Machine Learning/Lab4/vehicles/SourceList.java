/*
 * SourceList.java
 *
 * @author jrl -- Created on April 9, 2007, 2:58 PM
 *
 */

package vehicles;

import java.util.ArrayList;

public class SourceList extends ArrayList<AbstractSource> {
    
    private static final boolean DEBUG=true;
    
    /** Creates a new instance of SourceList */
    public SourceList() {
        super();
    }
    
    
    public String toString() {
        String returnMe = "SourceList: ";
        
        returnMe += super.toString();
        
        return returnMe;
    }
}
