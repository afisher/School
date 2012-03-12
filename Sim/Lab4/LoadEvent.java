public class LoadEvent extends Event {
    private String filename;

    public LoadEvent(long t, String f) {
        super(t);
        filename = f;
    }

    public void simulate() {
        Globals.FS.load(filename);
    }

    public String toString() {
        return "LoadEvent   filename = " + filename + super.toString();
    }
}
