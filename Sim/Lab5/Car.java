/* Car.java

    The car class is minimal.  All it does is paints itself by drawing an oval.
    In a more articulated simulation, all the characteristics pecular to a car would be
    here, like: maximum acceleration, handling, maximum braking.  Plus it might draw a 
    realistic looking car!
    
    All decisions about acceleration, braking and lane changes are made by the driver.
    
    Author: JRL, 10/00
*/

import java.awt.*;

class Car extends Vehicle {
    static final int WIDTH=5, LENGTH=8;
    
    Car () {}
    
    public Car(int pos, Color c, double ps, String name, Statistics s) {
        super(pos, c, ps, name, s);
    }

    public Car(int pos, Color c, double ps, String name, int driver) {
        super(pos, c, ps, name, new Statistics(1, driver));
    }

    public void paint(Graphics g, int y) {
        g.setColor(getColor());
        g.fillOval(getDisplayX(), y, LENGTH, WIDTH);
    }
    
}

