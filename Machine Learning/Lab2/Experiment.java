import java.util.*;
import javax.swing.*;

public class Experiment {
    private PatternList patterns;

    private ArrayList<ArrayList<Double>> results;

    private int [] thetas = {
        1, 10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000
    };
    private double [] trainingPercents = { 
        .10, .20, .30, .40, .50, .60, .70, .80, .90
    };

    private int runs = 10;

    public Experiment() {
        patterns = new PatternList();
        results  = new ArrayList<ArrayList<Double>>();
    }

    public PatternList getPatterns() { return patterns; }
    public void setPatterns(PatternList newPatterns) { patterns = newPatterns; }

    public ArrayList<ArrayList<Double>> getResults() { return results; }

    public void addPattern(Pattern p) {
        patterns.add(p);
    }

    public void run(JTextArea textArea) {
        for (int t : thetas) {
            ArrayList<Double> resultsRow = new ArrayList<Double>();
            Ptron curPtron;

            for (double p : trainingPercents) {
                curPtron = new Ptron(t, 1, p);
                curPtron.setPatterns(patterns);

                // run the ptron multiple times and average the results
                double total = 0;
                for (int i = 0; i < runs; i++) {
                    double r = curPtron.run();
                    total += r;
                }
                resultsRow.add(total / runs);
            }
            
            results.add(resultsRow);
        }

        //textArea.append(results.toString());
        
        Table table = new Table(thetas, trainingPercents, results);
        textArea.append(table.toString());
    }

}
