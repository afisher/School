
package ga;

/**
 *
 * @author levenick
 */
public interface Evaluable {
    public byte[] getDNA();
    public int getFitness();
    public void setFitness(int fitness);
    public Evaluable myClone();
    public void mutate();
}
