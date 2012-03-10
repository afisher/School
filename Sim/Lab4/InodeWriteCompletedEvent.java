public class InodeWriteCompletedEvent extends Event {
    Inode inode;
    String data;

    public InodeWriteCompletedEvent(long t, Inode i, String d) {
        super(t);
        inode = i;
        data = d;
    }

    public void simulate() {
        inode.store(data);

        if (Simulator.diskQ.isEmpty()) {
            Simulator.diskIdle = true;
        } else {
            Event event = Simulator.diskQ.poll();
            event.setTime(Simulator.time + Simulator.WRITE_TIME);
            Simulator.eventQ.add(event);
        }
    }

    public String toString() {
        return "InodeWriteCompletedEvent   data = " + data + super.toString();
    }
}
