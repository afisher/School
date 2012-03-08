import java.util.*;

public abstract class Event implements Comparable {
    protected int time;

    public Event() {
        time = 0;
    }

    public Event(int t) {
        time = t;
    }

    public int getTime() { return time; }

    public abstract void simulate();

    public int compareTo(Object other) {
        return time - ((Event)other).getTime();
    }
}
