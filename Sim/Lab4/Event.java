import java.util.*;

public abstract class Event implements Comparable {
    protected long time;

    public Event() {
        time = 0;
    }

    public Event(long t) {
        time = t;
    }

    public long getTime()       { return time; }
    public void setTime(long t) { time = t;    }

    public abstract void simulate();

    public int compareTo(Object other) {
        return (int)(time - ((Event)other).getTime());
    }
}
