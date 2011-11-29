package ga;

/**
 * GA_Runner.java   --  created on Oct 31, 2011, 1:55:09 PM
 * @author levenick
 * @author afisher
 */
public class GA_Runner extends Thread {

    public static final int[] sizes = { /*10, */100, 1000, 10000 };
    public static final double[] rates = { 0.001, 0.01, 0.1 };
    public static final int[] points = { 0, 1, 2, 5, 10 }; 

    public static final int[] types = { FitnessEvaluator.ONES, 
                                        FitnessEvaluator.FOUR,
                                        FitnessEvaluator.VEHICLES };

    public void run() {
        
        int[][] totRuns = new int[points.length][rates.length];

        int runs;

        for (Integer t : types) {
            /*if (t == FitnessEvaluator.VEHICLES) runs = 10;
            else runs = 1000;*/

            // set the max fitness we are looking for
            int max;
            int maxRuns;
            if (t == FitnessEvaluator.ONES) {
                max     = 100;
                maxRuns = 30000;
            } else if (t == FitnessEvaluator.FOUR) {
                max     = Fitness4.FIVE;
                maxRuns = 100000;
            } else {
                max = Population.MAX_TIME;
                maxRuns = 10;
            }

            for (Integer size : sizes) {
                int m = 0;
                for (Double r : rates) {
                    int n = 0;
                    for (Integer p : points) {
                        Population thePopulation = new Population(size, r, p);
                        
                        int total = 0;
                        runs = 0;

                        while (thePopulation.bestFitness() < max && runs < maxRuns) {
                        //for (int i = 0; i < runs; i++) {
                            thePopulation.evaluateFitness(t);
                            thePopulation.selectMatingPool();
                            //total += thePopulation.averageFitness();
                            runs++;
                        }

                        totRuns[n][m] = runs;

                        n++;
                    }
                    m++;
                }

                
                if (t == FitnessEvaluator.ONES) {
                    System.out.println("Number of runs for ONES function with population size " + size);
                } else if (t == FitnessEvaluator.FOUR) {
                    System.out.println("Number of runs for FOUR function with population size " + size);
                } else {
                    System.out.println("Number of runs for VEHICLES function with population size " + size);
                }
                System.out.println(table(totRuns));
                
            }
        }
    }

    public String table(int[][] fits) {
        StringBuilder ret = new StringBuilder();

        String spacer = "        |";
        ret.append(spacer);
        int width = spacer.length();

        for (Double r : rates) {
            String cur = String.format("      %3.3f |", r);
            ret.append(cur);
            width += cur.length();
        }
        ret.append("\n");

        for (int i = 0; i < width; i++) {
            ret.append("-");
        }
        ret.append("\n");

        for (int i = 0; i < points.length; i++) {
            ret.append(String.format("%7d |", points[i]));
            int[] row = fits[i];
            for (Integer r : row) {
                ret.append(String.format(" %10d |", r));
            }
            ret.append("\n");
        }
            
        return "" + ret;
    }
}
