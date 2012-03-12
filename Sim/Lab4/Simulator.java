import java.util.*;
import java.io.*;

public class Simulator {
    public static long time;
    public static boolean diskIdle;

    public static int WRITE_TIME = 10; // msec
    public static int READ_TIME  = 10; // msec

    public static ArrayList<Long> waitTimes = new ArrayList<Long>();

    public static PriorityQueue<Event> eventQ = new PriorityQueue<Event>();
    public static PriorityQueue<Event> diskQ  = new PriorityQueue<Event>();

    public static void init(java.io.File file) {
        time = 0;
        diskIdle = true;

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

                long timeConverted = timeMillis(timeUnconverted);

                if (type.equals("save")) {
                    String data = pieces[4];
                    eventQ.add(new SaveEvent(timeConverted, filename, data));
                } else if (type.equals("load")) {
                    eventQ.add(new LoadEvent(timeConverted, filename));
                } else if (type.equals("delete")) {
                    eventQ.add(new DeleteEvent(timeConverted, filename));
                }
            }

            printInfo();
            Main.textArea.append("\n==========");

        } catch (FileNotFoundException e) {
        }
    }

    public static void simulate() {
        while (!eventQ.isEmpty()) {
            Event curEvent = eventQ.poll();
            time = curEvent.getTime();
            curEvent.simulate();

            waitTimes.add(new Long(curEvent.getWaitTime()));
            printInfo();
            printSingleInfo(curEvent);
        }
        printTimeInfo();
    }

    public static void printInfo() {
        Main.textArea.append("\n\n" + "Event Queue:\n");
        if (!eventQ.isEmpty()) {
            for (Event e : eventQ) {
                Main.textArea.append(e.toString() + "\n");
            }
        } else {
            Main.textArea.append("Empty!\n");
        }

        Main.textArea.append("\n\n" + "Disk Queue:\n");
        if (!diskQ.isEmpty()) {
            for (Event e : diskQ) {
                Main.textArea.append(e.toString() + "\n");
            }
        } else {
            Main.textArea.append("Empty!\n");
        }

    }

    public static void printTimeInfo() {
        long max = 0;
        long total = 0;
        int timesWaited = 0;

        for (Long t : waitTimes) {
            if (t > max) max = t;

            total += t;

            if (t > 100) timesWaited++;
        }

        long average = total / waitTimes.size();

        Main.textArea.append("\nAverage wait time was " + average +
                             "\nMax wait time was " + max +
                             "\nEvents had to wait more than 100ms " + timesWaited + " times.");
    }

    public static void printSingleInfo(Event event) {
        Main.textArea.append("\n*** SIMULATING: " + event.toString());
        Main.textArea.append("\n==========");
    }

    public static void inodeSimulateStore(Inode inode, String data) {
        if (diskIdle) {
            diskIdle = false;
            eventQ.add(new InodeWriteCompletedEvent(time + WRITE_TIME, inode, data));
        } else {
            diskQ.add(new InodeWriteCompletedEvent(time + WRITE_TIME, inode, data));
        }
    }

    public static void blockSimulateStore(Block block, String data, boolean isLink) {
        if (diskIdle) {
            diskIdle = false;
            eventQ.add(new BlockWriteCompletedEvent(time + WRITE_TIME, block, data, isLink));
        } else {
            diskQ.add(new BlockWriteCompletedEvent(time + WRITE_TIME, block, data, isLink));
        }
    }

    public static void inodeSimulateLoad(Inode inode) {
        if (diskIdle) {
            diskIdle = false;
            eventQ.add(new InodeReadCompletedEvent(time + READ_TIME, inode));
        } else {
            diskQ.add(new InodeReadCompletedEvent(time + READ_TIME, inode));
        }
    }

    public static void blockSimulateLoad(Block block, int type) {
        if (diskIdle) {
            diskIdle = false;
            eventQ.add(new BlockReadCompletedEvent(time + READ_TIME, block, type));
        } else {
            diskQ.add(new BlockReadCompletedEvent(time + READ_TIME, block, type));
        }
    }

    // convert a string representation of time to time in milliseconds
    public static long timeMillis(String timeStr) {
        String[] pieces = timeStr.split(":");

        long ret;
        ret = new Long(pieces[0]);
        ret = ret *= 60; ret += new Long(pieces[1]);
        ret = ret *= 60; ret += new Long(pieces[2]);
        ret = ret *= 60; ret += new Long(pieces[3]);

        return ret;
    }

    // convert time in milliseconds to a printable time
    public static String timeString(long timeMs) {
        long hours = timeMs / (60 * 60 * 60);
        timeMs = timeMs % (60 * 60 * 60);

        long minutes = timeMs / (60 * 60);
        timeMs  = timeMs % (60 * 60);

        long seconds = timeMs / (60);
        timeMs = timeMs % 60;

        long mseconds = timeMs;

        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, mseconds);
    }
}
