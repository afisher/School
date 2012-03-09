public class SaveEvent extends Event {
    private String filename;
    private String data;

    public SaveEvent(int t, String f, String d) {
        super(t);
        filename = f;
        data = d;
    }

    public void simulate() {
    }
}
