public class SaveEvent extends Event {
    private String data;

    public SaveEvent(int t, String d) {
        super(t);
        data = d;
    }

    public void simulate() {
    }
}
