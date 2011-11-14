import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ashley Fisher
 */

public class Ptron {
	private PatternList patterns;
	private PatternList training;
	private PatternList testing;

	private ArrayList<Integer> weights;

	private int theta;
	private int eta;

	private boolean errors;

    private double testPercent; // percent of data to use for testing

    public Ptron() {
        patterns = new PatternList();
        training = new PatternList();
        testing  = new PatternList();
        weights  = new ArrayList<Integer>();

        theta = 10;
        eta = 1;

        errors = true;

        testPercent = 0.1;
    }

    public Ptron(int t, int e, double p) {
        patterns = new PatternList();
        weights = new ArrayList<Integer>();

        theta = t;
        eta = e;

        errors = true;

        testPercent = p;
    }

    public PatternList getPatterns() { return patterns; }
    public void setPatterns(PatternList newPatterns) { patterns = newPatterns; }

    public ArrayList<Integer> getWeights() { return weights; }
    public void setWeights(ArrayList<Integer> newWeights) { weights = newWeights; }

    public void addPattern(Pattern p) {
        patterns.add(p);
    }

    private void initWeights() {
        weights = new ArrayList<Integer>();

        for (Integer i : patterns.get(0).getVals()) {
		    weights.add(0);
        }
    }

    // randomly partition the data into training and testing
    private void partition() {
        training = new PatternList();
        testing  = new PatternList();

        Random randGen = new Random();
        ArrayList<Integer> added = new ArrayList<Integer>(); // indices of patterns already added to the test set

        int testNum = (int) (patterns.size() * testPercent);
        int randIndex = randGen.nextInt(patterns.size());

        for (int i = 0; i < testNum; i++) {
            // get a random index that hasn't already been used
            while(added.contains(randIndex)) {
                randIndex = randGen.nextInt(patterns.size());
            }

            // add the pattern at the random index to the test set
            testing.add(patterns.get(randIndex));
            added.add(randIndex);

            randIndex = randGen.nextInt(patterns.size());
        }

        // add all the non-testing patterns to the training set
        for (Pattern p : patterns) {
            if (!testing.contains(p)) {
                training.add(p);
            }
        }
   
    }

    // run the perceptron and return the result of test() 
    public double run() {
        initWeights();
        partition();

        train();
        return test();
    }

    // learn on the training set
    private void train() {
        boolean correct = false;

        int runs = 0, numErrors = 0;
        errors = true;

        // Run the perceptron
        while (errors) {
            errors = false;
            for (Pattern p : training) {
                correct = category(p); // check to see if it got this pattern correct
                if (correct != p.getCorrect()) {
                    errors = true;
                    numErrors++;
                    learn(p, correct);
                }
            }
            numErrors = 0;
            runs++;	
        }
    }

    // see how well the perceptron works on the test data
    private double test() {
        boolean correct;
        int numCorrect = 0;
        for (Pattern p : testing) {
            correct = category(p);
            if (correct == p.getCorrect()) {
                numCorrect++;
            }
        }

        return (numCorrect * 1.0) / (testing.size());
    }
        

    // compute whether or not the perceptron got a pattern correct
    private boolean category(Pattern p) {
        int sum = 0;
        for (int i = 0; i < weights.size(); i++) {
            sum += weights.get(i) * p.getVals().get(i);
        }

        return sum > theta; // above the threshold
    }

    // adjust the weights appropriately
    private void learn(Pattern p, boolean c) {
        errors = true;
        int expected, result;

        // translate boolean results to integers
        if (p.getCorrect()) { expected = 1; }
        else { expected = 0; }

        if (c) { result = 1; }
        else { result = 0; }

        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) + (expected - result) * p.getVals().get(i) * eta);
        }
    }
}
