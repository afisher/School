package vehicles;

/*
 * AbstractSensor.java
 *
 * Created on September 4, 2003, 9:56 AM
 */

/**
 *
 * @author  levenick
 */
public abstract class AbstractSensor {
    protected boolean crossed;
    
    public boolean getCrossed() {return crossed;}
    public void setCrossed(boolean c) {crossed = c;}
    abstract public String mySource();
}
