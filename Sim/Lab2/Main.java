import java.util.*;

public class Main {
    private static int h = 1000;
    private static int p = 100;

    private static final double A = 0.01;
    private static final double B = 0.01;

    private static final double ALPHA = 0.001;
    private static final double BETA  = 0.001;

    private static ArrayList<DataPair> data = new ArrayList<DataPair>();

    public static void main(String[] args) {
        data.add(new DataPair(h, p));
        output();

        while (h > 0 && p > 0) {
            step();
            output();
        }
    }

    private static void step() {
        double dH = A * h - ALPHA * h * p;
        double dP = BETA * h * p - B * p;

        h += dH;
        p += dP;

        data.add(new DataPair(h, p));
    }

    private static void output() {
        System.out.println("h = " + h + ", p = " + p);
    }
}
