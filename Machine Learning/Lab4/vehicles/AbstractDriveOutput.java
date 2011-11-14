package vehicles;

/*
 * AbstractDriveOutput.java
 *
 * Created on September 11, 2003, 4:06 PM
 */

/** la la la
 * @author levenick
 */
public class AbstractDriveOutput {
    static final int MAX_OUTPUT = 10;
    
    /** Creates a new instance of AbstractDriveOutput */
    public AbstractDriveOutput() {
    }
    /** left wheel output */    
    protected double leftWheelOutput;
    /** right wheel... */    
    protected double rightWheelOutput;

    /** accessor
     * @return accessor
     */    
    public double getLeftWheelOutput() {return leftWheelOutput;}
    /** accessor
     * @return accessor
     */    
    public double getRightWheelOutput() {return rightWheelOutput;}

    /** accessor
     * @param nuLeftWheelOutput the, uh, new left wheel output?
     */    
    public void setLeftWheelOutput(double nuLeftWheelOutput) {
        if (nuLeftWheelOutput>MAX_OUTPUT)
            leftWheelOutput = MAX_OUTPUT;
        else leftWheelOutput = nuLeftWheelOutput;
    }
    /** accessor
     * @param nuRightWheelOutput take a guess
     */    
    public void setRightWheelOutput(double nuRightWheelOutput) {
        if (nuRightWheelOutput>MAX_OUTPUT)
             rightWheelOutput = MAX_OUTPUT;
        else rightWheelOutput = nuRightWheelOutput;
    }
    
    /** to string?
     * @return Returns...
     */    
    public String toString() {
        String returnMe = "I am a DO: ";
        returnMe += "\tleft=" + leftWheelOutput;
        returnMe += "\tright=" + rightWheelOutput;
        return returnMe;
    } // toString()
    
}
