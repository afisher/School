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

    public String toString() {
        return "SaveEvent   filename = " + filename +
               "   data = " + data + super.toString() + "\n";
    }
}
