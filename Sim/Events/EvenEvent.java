import java.util.*;

public class EvenEvent extends Event {
    public static int count = 0;

    public EvenEvent(long v, int t) {
        super(v, t);
        count++;
    }

    public void simulate() {
        if (value > 1) {
            long newValue = value / 2;
            if (newValue % 2 == 0) {
                Main.pq.add(new EvenEvent(newValue, time + 1));
                //Main.pq.add(new EvenEvent(newValue, time + 1));
            } else if (newValue != 1) {
                Main.pq.add(new OddEvent(newValue, time + 1));
                //Main.pq.add(new OddEvent(newValue, time + 1));
            }
        }
    }
}
