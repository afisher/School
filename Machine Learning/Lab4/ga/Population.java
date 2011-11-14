package ga;

import java.util.*;

/**
 * Population.java   --  created on Oct 24, 2011, 12:01:55 PM
 * @author levenick
 * @author afisher
 */
public class Population extends ArrayList<Evaluable> {
    public static final int DNA_LENGTH=100;  // the Length of the string

    private static final int MAX_TIME=100000;
    public static int getMaxTime(){return MAX_TIME;}
    static int size;
    static double mutationRate;
    static int crossoverPoints;

    public Population(int s, double m, int c) {
        size = s;
        mutationRate = m;
        crossoverPoints = c;
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

    public Evaluable bestIndividual() {
        Evaluable ret = null;
        int bestFitness = 0;

        for (Evaluable nextInd: this) {
            if (nextInd.getFitness() > bestFitness) {
                ret = nextInd;
                bestFitness = ret.getFitness();
            }
        }

        return ret;
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

    // selects the mating pool
    // replaces the bottom two with the children of the top two
    // TODO implement the "real" version
    public void selectMatingPool() {
        Collections.sort((List)this);

        // get the mating pool (the top 2)
        ArrayList<Evaluable> matingPool = new ArrayList<Evaluable>();
        matingPool.add(this.get(this.size()-1).myClone());
        matingPool.add(this.get(this.size()-2).myClone());

        // replace the worst 2 with mutations of the best 2
        Evaluable parent1 = matingPool.get(0);
        Evaluable parent2 = matingPool.get(1);

        this.set(0, parent1.crossover(parent2, crossoverPoints));
        //this.get(0).mutate(mutationRate);
        this.set(1, parent2.crossover(parent1, crossoverPoints));
        //this.get(1).mutate(mutationRate);
    }
}
