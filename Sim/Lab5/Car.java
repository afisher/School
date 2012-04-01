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
    static Image ideeImage = Toolkit.getDefaultToolkit().createImage("car_idee.png");
    static Image ownImage = Toolkit.getDefaultToolkit().createImage("car_own.png");
    static Image aveImage = Toolkit.getDefaultToolkit().createImage("car_ave.png");
    static Image maxImage = Toolkit.getDefaultToolkit().createImage("car_max.png");

    Image myImage;
    
    Car () {}
    
    public Car(int pos, Color c, double ps) {
        super(pos, c, ps, View.AVE);
        System.out.println("Car!");
        myImage = aveImage;
    }

    public Car(int pos, Color c, double ps, int driver) {
        super(pos, c, ps, driver);

        switch (driver) {
            case View.IDEE: myImage = ideeImage; break;
            case View.OWN: myImage = ownImage; break;
            case View.AVE: myImage = aveImage; break;
            case View.MAX: myImage = maxImage; break;
            default: myImage = aveImage; break;
        }
    }

    public void paint(Graphics g, int y) {
        //g.setColor(getColor());
        //g.fillOval(getDisplayX(), y, LENGTH, WIDTH);
        //
        

        g.drawImage(myImage, getDisplayX(), y, LENGTH, WIDTH, null);
    }
    
}

