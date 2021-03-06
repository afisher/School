public class InodeWriteCompletedEvent extends Event {
    Inode inode;
    String data;

    public InodeWriteCompletedEvent(long t, Inode i, String d) {
        super(t);
        inode = i;
        data = d;
        bufferTime = Simulator.WRITE_TIME;
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
        return "InodeWriteCompletedEvent   sector# = " + inode.getNumber() + super.toString();
    }
}
