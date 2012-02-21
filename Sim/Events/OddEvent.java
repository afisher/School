import java.util.*;

public class OddEvent extends Event {
    public static int count = 0;

    public OddEvent(long v, int t) {
        super(v, t);
        count++;
    }

    public void simulate() {
        if (value > 1) {
            long newValue = value * 3 + 1;
            Main.pq.add(new EvenEvent(newValue, time + 10000));
        }
    }
}
