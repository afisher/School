/*  Experimenter.java

    The Experimenter for Phase II of the traffic program
    Starts up the controller, then prints the stats when it says it's done.
    
    Author: JRL 11/00

*/
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Experimenter {
    Controller theController;

    public Experimenter() {
        theController = new Controller(this);
        theController.init();
    }

    public static void main(String [] adsf) {
        new Experimenter();
    }
}
