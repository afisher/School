package ga;

import java.util.*;

/**
 * Population.java   --  created on Oct 24, 2011, 12:01:55 PM
 * @author levenick
 * @author afisher
 */
public class Population extends ArrayList<Evaluable> {
    public static final int DNA_LENGTH=100;  // the Length of the string

    public static final int MAX_TIME=10000;
    public static int getMaxTime(){return MAX_TIME;}
    static int size;
    static double mutationRate;
    static int crossoverPoints;
    static int matingPoolSize = 10;

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
    // replaces the bottom n with the children of the top n
    public void selectMatingPool() {
        // sort the individuals in descending order
        Collections.sort((List)this, Collections.reverseOrder());

        int sum = 0;
        for (Evaluable i : this) {
            sum += i.getFitness();
        }

        ArrayList<Evaluable> matingPool = new ArrayList<Evaluable>();
        Random gen = new Random();
        for (int i = 0; i < matingPoolSize; i++) {
            int r = gen.nextInt(sum);
            int index = 0;
            while (sum < r) {
                index++;
            }
            matingPool.add(this.get(index));
        }

        // mate the mating pool!
        mate(matingPool);
    }

    // does crossover on each consecutive pair in the mating pool
    private void mate(ArrayList<Evaluable> matingPool) {
        ArrayList<Evaluable> children = new ArrayList<Evaluable>();

        for (int i = 0; i < matingPool.size(); i += 2) {
            int j = i + 1;
            
            // if there is a pair, mate them
            if (j < matingPool.size()) {
                //System.out.println("Mating!");
                Evaluable parent1 = matingPool.get(i);
                Evaluable parent2 = matingPool.get(j);
                children.add(parent1.crossover(parent2, crossoverPoints));
            } else { // otherwise just add it to the children
                children.add(matingPool.get(i));
            }
        }

        // mutate the children
        for (Evaluable c : children) {
            c.mutate(mutationRate);
        }

        replacement(children);
    }

    // replace the bottom n individuals with the new children
    private void replacement(ArrayList<Evaluable> children) {
        for (int i = 0; i < children.size(); i++) {
            int j = this.size() - i;
            if (j >= 0 && j < this.size()) {
                this.set(this.size() - i, children.get(i));
            }
        }
    }
}
