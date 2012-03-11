public class BlockWriteCompletedEvent extends Event {
    Block block;
    String data;

    public BlockWriteCompletedEvent(long t, Block b, String d) {
        super(t);
        block = b;
        data = d;
    }

    public void simulate() {
        block.store(data);

        if (Simulator.diskQ.isEmpty()) {
            Simulator.diskIdle = true;
        } else {
            Event event = Simulator.diskQ.poll();
            event.setTime(Simulator.time + Simulator.WRITE_TIME);
            Simulator.eventQ.add(event);
        }
    }

    public String toString() {
        return "BlockWriteCompletedEvent   data = " + data + super.toString();
    }
}

