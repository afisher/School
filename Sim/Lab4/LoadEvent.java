public class LoadEvent extends Event {
    private String filename;

    public LoadEvent(int t, String f) {
        super(t);
        filename = f;
    }

    public void simulate() {
    }
}
