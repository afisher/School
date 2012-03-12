import java.util.*;

public abstract class Event implements Comparable {
    protected long time;
    protected long timeCreated;
    protected long waitTime;
    protected long bufferTime; // how long a read/write takes -- to be set per event type

    public Event() {
        time = 0;
        timeCreated = 0;
        bufferTime = 0;
    }

    public Event(long t) {
        time = t;
        bufferTime = 0;
    }

    public long getTime()       { return time;     }
    public long getWaitTime()   { return waitTime; }

    public void setTime(long t) { 
        timeCreated = time;
        time = t;

        waitTime = Math.max(0, (time - timeCreated) - bufferTime);
    }

    public abstract void simulate();

    public int compareTo(Object other) {
        return (int)(time - ((Event)other).getTime());
    }

    public String toString() {
        return "   scheduled time = " + Simulator.timeString(time) +
               "   current time = " + Simulator.timeString(Simulator.time) + 
               "   wait time = " + Simulator.timeString(waitTime);
    }
}
