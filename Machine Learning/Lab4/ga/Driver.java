/*
 * A prototype driver that creates a population and evaluates its fitness
 */

package ga;

/**
 *
 * @author levenick
 */
public class Driver {

    static int POP_SIZE = 1000;

    public static void main(String[] adsf) {
        Population thePopulation = new Population(POP_SIZE);
        thePopulation.evaluateFitness(FitnessEvaluator.FOUR);
        System.out.println("all done" + thePopulation);
    }

}


//        try {
//            java.awt.EventQueue.invokeAndWait(new Runnable() {
//                public void run() {
//                    initComponents();
//                }
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
