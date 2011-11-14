package ga;

import vehicles.*;

/**
 * FitnessEvaluator.java   --  created on Oct 26, 2011, 9:51:58 AM
 * @author levenick
 */
public class FitnessEvaluator {
    static GAVehiclesFrame theFrame = null;

    public static final int VEHICLES = 5;
    public static final int FOUR = 4;
    public static final int ONES = 1;

    public static void evaluateFitness(Population thePop, int which) {
        switch (which) {
            case VEHICLES:
                vehicles(thePop);
                break;
            case FOUR:
                four(thePop);
                break;
            case ONES:
                ones(thePop);
                break;
            default:
                System.out.println("oops, only 1, 4 & 5 presently");
        }
    }

    static void ones(Population thePop) {
        for (Evaluable nextInd : thePop) {
            nextInd.setFitness(Fitness1.getValue(nextInd));
        }
    }

    static void four(Population thePop) {
        for (Evaluable nextInd : thePop) {
            nextInd.setFitness(Fitness4.getValue(nextInd));
        }
    }

    static void vehicles(Population thePop) {
        Controller.setPopulation(thePop); // okay, okay, this is kinda sleazy
        Controller.time = 0; // this too!

        if (theFrame == null) {
            System.out.println("new frame...");
            theFrame = new GAVehiclesFrame();
        } else {
            theFrame.initController_World();
        }

        try {
            theFrame.theController.join(); // wait for the Controller to finish
        }catch (Exception e) {}
        
    }
}
