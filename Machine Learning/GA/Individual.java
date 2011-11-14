import java.util.*;

public class Individual implements Cloneable {
    private int fitness;
    private boolean[] dna;

    public Individual() {
        fitness = 0;
    }

    public Individual(int l) {
        dna = new boolean[l];

        dna = generateDNA();
        fitness = evaluateFitness();
    }

    public int getFitness()   { return fitness; }
    public boolean[] getDNA() { return dna;     }

    public void setDNA(boolean [] newDNA) {
        dna = newDNA;
        fitness = evaluateFitness();
    }

    public boolean[] generateDNA() {
        boolean[] ret = new boolean[dna.length];
        Random rand = new Random();
        for (int i = 0; i < dna.length; i++) {
            ret[i] = rand.nextBoolean();
        }
        return ret;
    }

    public int evaluateFitness() {
        int ret = 0;
        for (boolean b : dna) {
            if (b) ret++;
        }
        return ret;
    }

    public String toString() {
        String dnaString = "";
        for (boolean b : dna) {
            if (b) dnaString += "1";
            else dnaString += "0";
        }
        return "I am an individual!\nMy DNA is: " + dnaString + "\nMy fitness is: " + fitness;
    }

    public Object clone() {
        Individual ret = new Individual();
        ret.setDNA(dna);
        return ret;
    }
}
