import java.util.*;
import java.io.*;

public class Simulator {
    public static int time;

    public static PriorityQueue<Event> eventQ = new PriorityQueue<Event>();
    public static PriorityQueue<Event> diskQ  = new PriorityQueue<Event>();

    public static void init(java.io.File file) {
        time = 0;

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split(" ");

                // which logical device to save to -- worry about it later
                int deviceNum = Integer.parseInt(pieces[1]);

                String type            = pieces[0];
                String timeUnconverted = pieces[2];
                String filename        = pieces[3];

                int timeConverted = timeMillis(timeUnconverted);

                if (type.equals("save")) {
                    String data = pieces[4];
                    eventQ.add(new SaveEvent(timeConverted, filename, data));
                } else if (type.equals("load")) {
                    eventQ.add(new LoadEvent(timeConverted, filename));
                } else if (type.equals("delete")) {
                    eventQ.add(new DeleteEvent(timeConverted, filename));
                }

            }
        } catch (FileNotFoundException e) {
        }
    }

    public static void simulate() {
        while (!eventQ.isEmpty()) {
            Event curEvent = eventQ.poll();
            time = curEvent.getTime();
            curEvent.simulate();
        }
    }

    // convert a string representation of time to time in milliseconds
    public static int timeMillis(String timeStr) {
        String[] pieces = timeStr.split(":");

        int ret;
        ret =            Integer.parseInt(pieces[0]);
        ret = ret*60   + Integer.parseInt(pieces[1]);
        ret = ret*60   + Integer.parseInt(pieces[2]);
        ret = ret*1000 + Integer.parseInt(pieces[3]);

        return ret;
    }
}
