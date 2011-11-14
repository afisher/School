package ga;

/**
 * GA_Runner.java   --  created on Oct 31, 2011, 1:55:09 PM
 * @author levenick
 * @author afisher
 */
public class GA_Runner extends Thread {

    public static final int size = 100;
    public static final double rate = 0.1;
    public static final int points = 10;

    public void run() {
        //for (int size = 10; size <= 1000; size *= 10) {
            Population thePopulation = new Population(size, rate, points);
            for (int gen = 0; gen < 100; gen++) {
                thePopulation.evaluateFitness(FitnessEvaluator.ONES);
                System.out.println("\nall done\n" + thePopulation.stats());
                thePopulation.selectMatingPool();
            }
        //}
    }
}
