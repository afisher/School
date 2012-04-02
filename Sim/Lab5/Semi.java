import java.awt.*;

public class Semi extends Vehicle {
    static final int WIDTH=9, LENGTH=18;
    static Image ideeImage = Toolkit.getDefaultToolkit().createImage("semi_idee.png");
    static Image ownImage = Toolkit.getDefaultToolkit().createImage("semi_own.png");
    static Image aveImage = Toolkit.getDefaultToolkit().createImage("semi_ave.png");
    static Image maxImage = Toolkit.getDefaultToolkit().createImage("semi_max.png");
    static Image crazyImage = Toolkit.getDefaultToolkit().createImage("semi_crazy.png");

    private Image myImage;

    public Semi(int pos, Color c, double ps) {
        super(pos, c, ps, View.AVE);
        myImage = aveImage;
    }

    public Semi(int pos, Color c, double ps, int driver) {
        super(pos, c, ps, driver);

        switch (driver) {
            case View.IDEE: myImage = ideeImage; break;
            case View.OWN: myImage = ownImage; break;
            case View.AVE: myImage = aveImage; break;
            case View.MAX: myImage = maxImage; break;
            case View.CRAZY: myImage = crazyImage; break;
            default: myImage = aveImage; break;
        }
    }

    protected void moveForward() {
        location += speed / 20;
    }

    public void paint(Graphics g, int y) {
        //g.setColor(getColor());
        //g.fillOval(getDisplayX(), y, LENGTH, WIDTH);

        g.drawImage(myImage, getDisplayX(), y, LENGTH, WIDTH, null);
    }
}
