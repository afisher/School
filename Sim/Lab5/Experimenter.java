/*  Experimenter.java

    The Experimenter for Phase II of the traffic program
    Starts up the controller, then prints the stats when it says it's done.
    
    Author: JRL 11/00

*/
import java.awt.*;
import java.util.*;

public class Experimenter extends Frame {
    Controller theController;

    public Experimenter() {
        theController = new Controller(this);
        theController.init();
        //display(theController);
        //theController.start();
        setLayout(new BorderLayout());
        add(theController);
        System.out.println("inited!");
        setBounds(30,100,1200,300);
        show();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
    }

    public static void main(String [] adsf) {
        new Experimenter();
    }

    /*public void finishedSir(Vector stats) {
        MyWriter out = new MyWriter();
        System.out.println("finished!");
        out.println(Statistics.toString(stats));
        System.out.println(Statistics.toString(stats));
        out.close();
    }*/
}
