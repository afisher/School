package ga;

import java.util.ArrayList;

/**
 * Population.java   --  created on Oct 24, 2011, 12:01:55 PM
 * @author levenick
 */
public class Population extends ArrayList<Evaluable> {
    public static final int DNA_LENGTH=100;  // the Length of the string

    private static final int MAX_TIME=100000;
    public static int getMaxTime(){return MAX_TIME;}
    static int size;

    public Population(int s) {
        size = s;
        init();
    }

    void init() {
        this.clear();
        for (int i = 0; i < size; i++) {
            add(new Individual(randDNA()));
        }
    }

    @Override
    public String toString() {
        String returnMe = "Population: ";

        for (Evaluable nextE : this) {
            returnMe += nextE.toString();
        }

        returnMe += stats();

        return returnMe;
    }

    public String stats() {
        return "\nsize=" + size + " maxFitness=" + bestFitness() + " ave=" + averageFitness();
    }

    void evaluateFitness(int which) {
        FitnessEvaluator.evaluateFitness(this, which);
    }

    public int bestFitness() {
        int returnMe=0;

        for (Evaluable nextInd: this) {
            if (nextInd.getFitness()>returnMe) {
                returnMe = nextInd.getFitness();
            }
        }

        return returnMe;
    }

    public int averageFitness() {
        int sum=0;

        for (Evaluable nextInd: this) {
            sum += nextInd.getFitness();
        }

        return (int) (sum/size());
    }


    byte[] randDNA() {
        byte[] returnMe = new byte[DNA_LENGTH];

        for (int i = 0; i < returnMe.length; i++) {
            if (Math.random() > 0.5) {
                returnMe[i] = 1;
            }
        }

        return returnMe;
    }
}
