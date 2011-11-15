package vehicles;

/*
 * ProtoWorld.java
 *
 * Created on September 11, 2003, 11:59 AM
 */
/**
 *
 * @author  levenick
 */
import ga.Population;
import ga.Evaluable;
import java.awt.geom.Point2D;
import java.awt.*;

public class ProtoWorld extends AbstractWorld {

    public static final int IMAGE_SIZE = 800;
    boolean debugging = false;

    public void clobberList() {
        theVehicleList.clear();
    }

    /** Creates a new instance of ProtoWorld */
    public ProtoWorld(Population list) {
        super();
        //System.out.println("vl=" + theVehicleList);
        addSource(new LightSource(new Point2D.Double(300, 300), 1655));
        addSource(new LightSource(new Point2D.Double(320, 300), 100));
        addSource(new LightSource(new Point2D.Double(500, 300), 8775));
        addSource(new LightSource(new Point2D.Double(500, 320), 100));
        addSource(new LightSource(new Point2D.Double(300, 500), 6654));
        addSource(new LightSource(new Point2D.Double(300, 520), 200));
        addSource(new LightSource(new Point2D.Double(500, 500), 1867));
        addSource(new LightSource(new Point2D.Double(520, 500), 200));

        createVehicles(list);
    }

    void createVehicles(Population list) {
        if (list == null) {
            for (int x = 0; x < 2000; x++) {
                //addVehicle(new ProtoVehicle(new Point2D.Double(400, 800), Math.PI/2, randBool()));
                addVehicle(new ProtoVehicle(new Point2D.Double(400, 400), rand(10) * Math.PI / 4, randBool()));
            }
        } else {
            //System.out.println("adding vehicles!");
            for (Evaluable nextDNA : list) {
                //System.out.print(".");
                AbstractVehicle newV = new ProtoVehicle(new Point2D.Double(dnaToX(nextDNA), dnaToY(nextDNA)), dnaToSize(nextDNA),
                        rand(10) * Math.PI / 4,
                        dnaToBool(nextDNA));
                addVehicle(newV);
                newV.setInd(nextDNA);
            }
        }
    }
    int cross_start = 0;
    int X_start = 10;
    int Y_start = 25;
    int size_start = 40;
    int X_length = 11;
    int Y_length = 11;
    int size_length = 11;

    double dnaToX(Evaluable theDNA) {
        return (double) toInt(theDNA, X_start, X_length);
    }

    double dnaToY(Evaluable theDNA) {
        return (double) toInt(theDNA, Y_start, Y_length);
    }

    int dnaToSize(Evaluable theDNA) {
        return toInt(theDNA, size_start, size_length);
    }

    boolean dnaToBool(Evaluable theDNA) {
        return theDNA.getDNA()[cross_start] == 1;
    }

    int toInt(Evaluable theDNA, int start, int length) {
        byte[] bytes = theDNA.getDNA();
        int sum = 0;
        int value = 1;

        for (int i = start; i < start + length; i++) {
            if (bytes[i] == 1) {
                sum += value;
            }
            value *= 2;
        }

        return sum;
    }

    private boolean randBool() {
        return Math.random() < 0.9;
    }

    private int rand(int max) {
        return (int) (Math.random() * max);
    }

    public void addSource(AbstractSource nuSource) {
        theSourceList.add(nuSource);
    }

    public void addVehicle(AbstractVehicle nuVehicle) {
        theVehicleList.add(nuVehicle);
    }

    public double getStimulusStrength(Point2D.Double where) {
        double sum = 0;

        for (AbstractSource nextLightSource : theSourceList) {
            double d = where.distance(nextLightSource.getLocation());
            sum += nextLightSource.getIntensity() / (d * d);
        }

        return sum;
    }

    public void step() {
        for (AbstractVehicle nextVehicle : theVehicleList) {
            AbstractDriveOutput theOutput = nextVehicle.step(this);
            moveIt(nextVehicle, theOutput);
        }
        fallOffTable();
    }

    private void fallOffTable() {
        VehicleList deadList = new VehicleList();

        for (AbstractVehicle nextVehicle : theVehicleList) {
            if (outOfBounds(nextVehicle)) {
                deadList.add(nextVehicle);
            }
        }

        synchronized (this) {
            for (AbstractVehicle nextVehicle : deadList) {
                killIt(nextVehicle);
            }
        }
    }

    private void moveIt(AbstractVehicle theVehicle, AbstractDriveOutput theOutput) {
        if (debugging) {
            System.out.println("Applying " + theOutput + " to  " + theVehicle);
        }
        double leftOutput = theOutput.getLeftWheelOutput();
        double rightOutput = theOutput.getRightWheelOutput();
        double direction = theVehicle.getOrientation();

        double distance = (leftOutput + rightOutput) / 2;
        double dx = distance * Math.cos(direction);
        double dy = -distance * Math.sin(direction);
        double x = theVehicle.getLocation().getX();
        double y = theVehicle.getLocation().getY();
        theVehicle.setLocation(new Point2D.Double(x + dx, y + dy));

        double deltaDirection = ((rightOutput - leftOutput) / theVehicle.getSize()) * (Math.PI / 4);
        theVehicle.setOrientation(direction + deltaDirection);
    }

    void killIt(AbstractVehicle killMe) {
        killMe.getInd().setFitness(Controller.time);
        theVehicleList.remove(killMe);
    }

    void killEmAll() {
        for (AbstractVehicle nextVehicle : theVehicleList) {
            killIt(nextVehicle);
        }
    }

    void setFitnesses() {
        for (AbstractVehicle nextVehicle : theVehicleList) {
            nextVehicle.getInd().setFitness(Controller.time);
        }
    }

    boolean outOfBounds(AbstractVehicle v) {
        double x = v.getX();
        double y = v.getY();
        return out(x, v.getSize()) || out(y, v.getSize());
    }

    boolean out(double x, int size) {
        return x + size < 0 || x - size < 0 || x + size > IMAGE_SIZE || x - size > IMAGE_SIZE;
    }

    public String toString() {
        return "ProtoWorld";
    }
    private static Image offScreenImage;
    private static Graphics offG;

    public void paint(Graphics g) {
        //System.out.print("w");
        if (offG == null) {
            System.out.print(" null! ");
            return;
        }
        offG.setColor(Color.white);
        offG.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        for (AbstractSource nextLightSource : theSourceList) {
            nextLightSource.paint(offG);
        }
        for (AbstractVehicle nextVehicle : theVehicleList) {
            nextVehicle.paint(offG);
        }
        g.drawImage(offScreenImage, 0, 100, null);
        g.drawImage(offScreenImage, 0, 0, drawR.width, drawR.height, null);
    }

    /**
     * @param aOffScreenImage the offScreenImage to set
     */
    public static void setOffScreenImage(Image aOffScreenImage) {
        offScreenImage = aOffScreenImage;
        setOffG(offScreenImage.getGraphics());
    }

    /**
     * @param aOffG the offG to set
     */
    public static void setOffG(Graphics aOffG) {
        offG = aOffG;
    }

    public static void clobberImage() {
        offScreenImage = null;
    }

    static Rectangle drawR;
    public static void setDrawSize(Rectangle r) {
        drawR = r;
    }
}
