package vehicles;

/*
 * MyDriveOutput.java
 *
 * Created on September 5, 2003, 10:54 AM
 */

/**
 *
 * @author  wu_staff
 */
public class MyDriveOutput extends AbstractDriveOutput {
    
    /** Creates a new instance of MyDriveOutput */
    public MyDriveOutput() {
    }
    
    public MyDriveOutput(double left, double right) {
        this.setLeftWheelOutput(left);
        this.setRightWheelOutput(right);
    }
    
    public String toString() {
        return "myD" + super.toString();
    }
}
