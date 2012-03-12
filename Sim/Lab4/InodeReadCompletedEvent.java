public class InodeReadCompletedEvent extends Event {
    Inode inode;

    public InodeReadCompletedEvent(long t, Inode i) {
        super(t);
        inode = i;
        bufferTime = Simulator.READ_TIME;
    }

    public void simulate() {
        inode.load();

        if (Simulator.diskQ.isEmpty()) {
            Simulator.diskIdle = true;
        } else {
            Event event = Simulator.diskQ.poll();
            event.setTime(Simulator.time + Simulator.WRITE_TIME);
            Simulator.eventQ.add(event);
        }
    }

    public String toString() {
        return "InodeReadCompletedEvent   sector# = " + inode.getNumber() + super.toString();
    }
}
