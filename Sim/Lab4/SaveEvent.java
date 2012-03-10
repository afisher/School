public class SaveEvent extends Event {
    private String filename;
    private String data;

    public SaveEvent(long t, String f, String d) {
        super(t);
        filename = f;
        data = d;
    }

    public void simulate() {
        Globals.FS.save(filename, data);
    }
}
