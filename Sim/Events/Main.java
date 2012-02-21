import java.util.*;

public class Main {
    public static PriorityQueue<Event> pq = new PriorityQueue<Event>();
    public static int time;

    public static void runOnce() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the initial value.");
        int value = in.nextInt();

        time = 0;

        runIt(value);

        System.out.println(time + " ms");
        System.out.println(EvenEvent.count + " even events");
        System.out.println(OddEvent.count + " odd events");
    }

    public static void runMany() {
        int n = 1000000;
        int maxTime = 0;
        long maxVal = 0;

        for (int value = 1; value < n; value++) {
            time = 0;

            runIt(value);

            if (time > maxTime) {
                maxTime = time;
                maxVal  = value;
            }
        }

        System.out.println("Maximum initial value was " + maxVal + " which took " + maxTime + " ms.");
    }

    private static void runIt(int value) {
        if (value > 1) {
            if (value % 2 == 0) {
                pq.add(new EvenEvent((long)value, time + 1));
            } else {
                pq.add(new OddEvent((long)value, time + 10000));
            }
        }

        while (pq.size() > 0) {
            Event currentEvent = pq.poll();
            time = currentEvent.getTime();
            currentEvent.simulate();
        }
    }

    public static void main(String [] args) {
        //runOnce();
        runMany();
    }
}
