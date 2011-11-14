package vehicles;

/*
 * Controller.java
 *
 * Created on September 11, 2003, 11:13 AM
 */
/**
 *
 * @author  levenick
 */
import ga.Population;
import java.awt.*;
import javax.swing.JTextField;

/** Runs the World, Threaded so the animation works. */
public class Controller extends Thread {

    Frame theFrame;
    int stepsToTake;
    public ProtoWorld theWorld;
    private boolean running = true;
    public static int time = 0;
    JTextField theTF;
    static Population thePopulation = null;
    private int stopTime;

    public void setStop(int s) {
        stopTime = s;
    }

    public void toggleRun() {running = !running;}

    public static void setPopulation(Population p) {
        thePopulation = p;
    }

    /** Creates a new instance of Controller
     * @param theFrame The calling Frame, so the Controller can repaint() it.
     */
    public Controller(Frame theFrame, JTextField t) {
        this.theFrame = theFrame;
        theTF = t;
        theWorld = new ProtoWorld(thePopulation);
        stopTime = Population.getMaxTime();
    }

    /** Paint the World
     * @param g The screen
     */
    public void paint(Graphics g) {
        synchronized (theWorld) {
            theWorld.paint(g);
        }
        //System.out.print("c");
    }

    /** Add one to the number of steps to take. */
    public void incSteps() {
        stepsToTake++;
    }

    /** Forever loop, controlled by the variable stepsToTake */
    @Override
    public void run() {
        System.out.println("running... stoptime=" + stopTime);
        for (;;) {
            //System.out.print("^");
            if (running) {
                time++;
                step();
                if (time >= stopTime || theWorld.theVehicleList.isEmpty()) {
                    break;
                }
                if (time>stopTime) {
                    System.out.println("Impossible!! time=" + time + " stopTime=" + stopTime);
                }
            }
            fancyDelay();
        }
        theWorld.setFitnesses();
    }

    void fancyDelay() {
        if (time>0 && time<10) {
            delay(500/time);
        } else {
            delay(0);
        }
    }


    /** Does one step -- step, repaint, pause */
    private void step() {
        synchronized (theWorld) {
            theWorld.step();
        }
        theTF.setText("" + time);
        theFrame.repaint();
    }

    /** Waits
     * @param t num milliseconds to pause
     *
     */
    private void delay(int t) {
        try {
            sleep(t);  // useful, eh?
        } catch (Exception e) {
        }
    }

    public boolean getRunning() {
        return running;
    }

    public void toggleRunning() {
        running = !running;
    }
}
