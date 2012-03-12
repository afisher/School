public class BlockWriteCompletedEvent extends Event {
    private Block block;
    private String data;
    private boolean isLink;

    public BlockWriteCompletedEvent(long t, Block b, String d, boolean l) {
        super(t);
        block = b;
        data = d;
        isLink = l;
        bufferTime = Simulator.WRITE_TIME;
    }

    public void simulate() {
        if (!isLink) {
            block.store(data);
        } else {
            for (int i = 0; i < Inode.LINKS_PER_BLOCK * 2; i+=2) {
                if (i < data.length()) {
                    int num = Integer.parseInt(data.substring(i, Math.min(data.length(), i+2)));
                    block.setBlockNumber(i % 2, num);
                }
            }
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
        if (!isLink) {
            return "BlockWriteCompletedEvent   sector# = " + block.getNumber() + 
                   "   data = " + data + super.toString();
        } else {
            return "BlockWriteCompletedEvent   sector# = " + block.getNumber() +
                   "   links = " + data + super.toString();
        }
    }
}

