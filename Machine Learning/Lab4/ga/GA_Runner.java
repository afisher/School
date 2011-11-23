package ga;

/**
 * GA_Runner.java   --  created on Oct 31, 2011, 1:55:09 PM
 * @author levenick
 * @author afisher
 */
public class GA_Runner extends Thread {

    public static final int[] sizes = { 10, 100, 1000, 10000 };
    public static final double[] rates = { 0.001, 0.01, 0.1 };
    public static final int[] points = { 0, 1, 2, -1 }; // -1 indicates n-point crossover

    public static final int[] types = { FitnessEvaluator.ONES, 
                                        FitnessEvaluator.FOUR,
                                        FitnessEvaluator.VEHICLES };

    public void run() {
        
        int[][] avgFits = new int[points.length][rates.length];

        int runs;

        for (Integer t : types) {
            if (t == FitnessEvaluator.VEHICLES) runs = 10;
            else runs = 1000;

            for (Integer size : sizes) {
                int m = 0;
                for (Double r : rates) {
                    int n = 0;
                    for (Integer p : points) {
                        Population thePopulation = new Population(size, r, p);
                        
                        int total = 0;

                        for (int i = 0; i < runs; i++) {
                            thePopulation.evaluateFitness(t);
                            thePopulation.selectMatingPool();
                            total += thePopulation.averageFitness();
                        }

                        avgFits[n][m] = total / runs;

                        n++;
                    }
                    m++;
                }

                if (t == FitnessEvaluator.ONES) {
                    System.out.println("Average fitness over " + runs + " generations with ONES function with population size " + size);
                } else if (t == FitnessEvaluator.FOUR) {
                    System.out.println("Average fitness over " + runs + " generations with FOUR function with population size " + size);
                } else {
                    System.out.println("Average fitness over " + runs + " generations with VEHICLES function with population size " + size);
                }
                System.out.println(table(avgFits));
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
