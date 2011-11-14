package ga;

/**
 * GA_Runner.java   --  created on Oct 31, 2011, 1:55:09 PM
 * @author levenick
 */
public class GA_Runner extends Thread {

    public void run() {
        for (int size = 10; size <= 1000; size *= 10) {
            Population thePopulation = new Population(size);
            thePopulation.evaluateFitness(FitnessEvaluator.VEHICLES);
            System.out.println("\nall done\n" + thePopulation.stats());
        }
    }
}
