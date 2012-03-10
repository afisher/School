public class DeleteEvent extends Event {
    private String filename;

    public DeleteEvent(long t, String f) {
        super(t);
        filename = f;
    }

    public void simulate() {
    }
}
