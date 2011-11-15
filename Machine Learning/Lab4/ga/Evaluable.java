
package ga;

import java.util.*;

/**
 *
 * @author levenick
 */
public interface Evaluable extends Comparable<Evaluable> {
    public byte[] getDNA();
    public void setDNA(byte[] dna);
    public int getFitness();
    public void setFitness(int fitness);
    public Evaluable myClone();
    public void mutate(double rate);
    public Evaluable crossover(Evaluable other, int points);
}
