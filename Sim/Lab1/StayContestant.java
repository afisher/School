import java.util.Random;

public class StayContestant implements Contestant {
    private Random randGen = new Random();

    public int guess() {
        return randGen.nextInt(3); 
    }

    public boolean change() {
        return false;
    }
}
