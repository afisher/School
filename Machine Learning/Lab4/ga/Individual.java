package ga;

/**
 * Individual.java   --  created on Oct 24, 2011, 12:52:02 PM
 * @author levenick
 */
public class Individual implements Evaluable, Comparable<Evaluable>, Cloneable {

    byte[] dna;
    private int fitness;

    private static final double MUTATION_RATE = 0.01;

    public int compareTo(Evaluable that) {
        if (this.getFitness() > that.getFitness()) {
            return -1;
        }
        return 1;
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

    public void mutate() {
        for (int i = 0; i < dna.length; i++) {
            if (Math.random() < MUTATION_RATE) {
                dna[i] = (byte)(Math.abs(dna[i] - 1));
            }
        } 
    }
}

