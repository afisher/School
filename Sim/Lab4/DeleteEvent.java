public class DeleteEvent extends Event {
    private String filename;

    public DeleteEvent(long t, String f) {
        super(t);
        filename = f;
    }

    public void simulate() {
        Globals.FS.delete(filename); 
    }

    public String toString() {
        return "DeleteEvent   filename = " + filename + super.toString();
    }
}
