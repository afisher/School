public class BlockReadCompletedEvent extends Event {
    private Block block;
    private int type;
    private String data;

    public BlockReadCompletedEvent(long t, Block b, int ty) {
        super(t);
        block = b;
        type = ty;
        bufferTime = Simulator.WRITE_TIME;
    }

    public void simulate() {
        switch(type) {
            case Block.TYPE_DIRECT:
                data = block.loadDirect();
                break;
            case Block.TYPE_SINGLE:
                block.loadSingleIndirect();
                break;
            case Block.TYPE_DOUBLE:
                block.loadDoubleIndirect();
                break;
            default: break;
        }

        if (Simulator.diskQ.isEmpty()) {
            Simulator.diskIdle = true;
        } else {
            Event event = Simulator.diskQ.poll();
            event.setTime(Simulator.time + Simulator.WRITE_TIME);
            Simulator.eventQ.add(event);
        }
    }

    public String toString() {
        return "BlockReadCompletedEvent   sector# = " + block.getNumber() + 
               "   data = " + data + super.toString();
    }
}


