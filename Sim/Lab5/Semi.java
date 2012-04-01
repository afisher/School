import java.awt.*;

public class Semi extends Vehicle {
    static final int WIDTH=5, LENGTH=16;
    static Image image = Toolkit.getDefaultToolkit().createImage("semi.jpg");

    public Semi(int pos, Color c, double ps) {
        super(pos, c, ps, View.AVE);
        System.out.println("say what?");
    }

    public Semi(int pos, Color c, double ps, int driver) {
        super(pos, c, ps, driver);
    }

    protected void moveForward() {
        location += speed / 20;
    }

    public void paint(Graphics g, int y) {
        //g.setColor(getColor());
        //g.fillOval(getDisplayX(), y, LENGTH, WIDTH);

        g.drawImage(image, getDisplayX(), y, LENGTH, WIDTH, null);
    }
}
