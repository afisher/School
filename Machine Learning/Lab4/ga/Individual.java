package ga;

import java.util.*;

/**
 * Individual.java   --  created on Oct 24, 2011, 12:52:02 PM
 * @author levenick
 * @author afisher
 */
public class Individual implements Evaluable, Cloneable {

    byte[] dna;
    private int fitness;

    public int compareTo(Evaluable that) {
        return fitness - that.getFitness();
        /*for (int i = 0; i < dna.length; i++) {
            if (dna[i] != that.getDNA()[i]) {
            }
        }
        return 0;*/
    }

    public Individual myClone() {
        try {
            Individual returnMe = (Individual) super.clone();
            returnMe.dna = new byte[dna.length];
            for (int i = 0; i < dna.length; i++) {
                returnMe.dna[i] = dna[i];
            }
            return returnMe;
        } catch (Exception e) {System.out.println("now you've done it!! " + e);
        }
        return null;
    }

    public Individual(byte[] bits) {
        dna = bits;
    }

    public byte[] getDNA() {
        return dna;
    }

    public void setDNA(byte[] newDNA) {
        dna = newDNA;
    }

    public String toString() {
        String returnMe = "Individual: fitness=" + fitness + "\t";

        for (int i = 0; i < dna.length; i++) {
            returnMe += "" + dna[i];
        }

        return returnMe + "\n";
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void mutate(double rate) {
        for (int i = 0; i < dna.length; i++) {
            if (Math.random() < rate) {
                dna[i] = (byte)(Math.abs(dna[i] - 1));
            }
        } 
    }

    // cross the individual with another at a specified number of points
    // order matters!
    public Evaluable crossover(Evaluable other, int points) {
        byte[] firstParent  = dna;
        byte[] secondParent = other.getDNA();

        byte[] child = new byte[dna.length];

        // generate a list of random crossover points
        ArrayList<Integer> pointsList = new ArrayList<Integer>();
        for (int n = 0; n < points; n++) {
            Random gen = new Random();
            int p = gen.nextInt(dna.length);
            if (!pointsList.contains(p)) pointsList.add(p);
        }

        // sort the points
        Collections.sort((List)pointsList);

        int i = 0;
        int c = 0;
        for (Integer p : pointsList) {
            while (i < p) {
                if (c % 2 == 0) {
                    child[i] = firstParent[i];
                } else {
                    child[i] = secondParent[i];
                }
                i++;
            }
            c++;
        }

        if (points == 0) child = firstParent;

        Individual ret = new Individual(child);

        return ret;
    }
}

