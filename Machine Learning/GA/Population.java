import java.util.*;
import javax.swing.*;

public class Population {
    private ArrayList<Individual> individuals;
    private static final int L = 10;

    public Population() {
        individuals = new ArrayList<Individual>();
    }

    public Population(int size) {
        individuals = new ArrayList<Individual>();
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(L));
        }
    }

    public ArrayList<Individual> getIndividuals() { return individuals; }

    public void evaluateFitness(JTextArea textArea) {
        textArea.append("Evaluate fitness!\n");
    }

    public void selectMatingPool(JTextArea textArea) {
        textArea.append("Select mating pool!\n");
    }

    public void applyGeneticOperators(JTextArea textArea) {
        textArea.append("Apply genetic operators!\n");
    }

    public void replacement(JTextArea textArea) {
        textArea.append("Do replacement!\n");
    }

    public String toString() {
        String ret = "";
        for (Individual ind : individuals) {
            ret += ind.toString() + "\n\n";
        }
        return ret;
    }
}
