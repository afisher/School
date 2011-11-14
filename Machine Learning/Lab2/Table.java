import java.util.*;
import javax.swing.*;

public class Table {
    private int [] thetas;
    private double [] percents;

    private ArrayList<ArrayList<Double>> contents;

    public Table() {
        contents = new ArrayList<ArrayList<Double>>();
    }

    public Table(int [] t, double [] p, ArrayList<ArrayList<Double>> c) {
        thetas = t;
        percents = p;
        contents = c;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();

        String spacer = "      |";
        ret.append(spacer);
        int width = spacer.length();

        for (double p : percents) {
            String cur = String.format(" %8.0f%% |", p * 100);
            ret.append(cur);
            width += cur.length();
        }
        ret.append("\n");

        for (int i = 0; i < width; i++) {
            ret.append("-");
        }
        ret.append("\n");

        for (int i = 0; i < thetas.length; i++) {
            ret.append(String.format("%5d |", thetas[i]));
            ArrayList<Double> contentRow = contents.get(i);
            for (Double c : contentRow) {
                ret.append(String.format(" %9.3f |", c));
            }
            ret.append("\n");
        }

        return "" + ret;
    }
}
