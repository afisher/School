import java.util.*;
import java.io.*;

public class Simulator {
    public static PriorityQueue<Event> eventQ = new PriorityQueue<Event>();
    public static PriorityQueue<Event> diskQ  = new PriorityQueue<Event>();

    public Simulator(java.io.File file) {
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
                    eventQ.add(new SaveEvent(timeConverted, data));
                } else if (type.equals("load")) {
                    // add load event
                } else if (type.equals("delete")) {
                    // add delete event
                }

            }
        } catch (FileNotFoundException e) {
        }
    }

    // convert a string representation of time to time in milliseconds
    private int timeMillis(String timeStr) {
        String[] pieces = timeStr.split(":");

        int ret;
        ret =            Integer.parseInt(pieces[0]);
        ret = ret*60   + Integer.parseInt(pieces[1]);
        ret = ret*60   + Integer.parseInt(pieces[2]);
        ret = ret*1000 + Integer.parseInt(pieces[3]);

        return ret;
    }
}
