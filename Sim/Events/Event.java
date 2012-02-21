import java.util.*;

public abstract class Event implements Comparable {
    protected long value;
    protected int time;

    public Event() {
        value = 0;
        time  = 0;
    }

    public Event(long v, int t) {
        value = v;
        time  = t;
    }

    public long getValue() { return value; }
    public int getTime()  { return time; }

    public abstract void simulate();

    public int compareTo(Object other) {
        return time - ((Event)other).getTime();
    }
}
