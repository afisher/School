import java.util.ArrayList;

public class Block extends Sector {
    public static final int TYPE_DIRECT = 0;
    public static final int TYPE_SINGLE = 1;
    public static final int TYPE_DOUBLE = 2;

    public static final int BLOCK_LENGTH = 8;

    private byte[] bytes;

    public Block(int n) {
        super(n);
        bytes = new byte[BLOCK_LENGTH];
    }

    public void startStore(String s, boolean isLink) {
        if (s.length() > BLOCK_LENGTH) {
            Globals.complain("That won't fit!");
            return;
        }

        simulateStore(s, isLink);
    }

    public void simulateStore(String s, boolean isLink) {
        Simulator.blockSimulateStore(this, s, isLink);
    }

    public void store(String s) {
        for (int i = 0; i < s.length(); i++) {
            bytes[i] = (byte)(s.charAt(i));
        }
    }

    public void startLoadDirect() {
        simulateLoadDirect();
    }

    public void startLoadSingleIndirect() {
        simulateLoadSingleIndirect();
    }

    public void startLoadDoubleIndirect() {
        simulateLoadDoubleIndirect();
    }

    public void simulateLoadDirect() {
        Simulator.blockSimulateLoad(this, TYPE_DIRECT);
    }

    public void simulateLoadSingleIndirect() {
        Simulator.blockSimulateLoad(this, TYPE_SINGLE);
    }

    public void simulateLoadDoubleIndirect() {
        Simulator.blockSimulateLoad(this, TYPE_DOUBLE);
    }

    public String load() {
        String ret = "";

        int i = 0;
        while (i < BLOCK_LENGTH && bytes[i] != 0) {
            ret += (char)(bytes[i]);
            i++;
        }

        return ret;
    }

    public String loadDirect() {
        return load();
    }

    public void loadSingleIndirect() {
        //String ret = "";
        
        for (Block b : getBlocks()) {
            Simulator.blockSimulateLoad(b, TYPE_DIRECT);
        }

        //return ret;
    }

    public void loadDoubleIndirect() {
        //String ret = "";

        for (Block link : getBlocks()) {
            Simulator.blockSimulateLoad(link, TYPE_DIRECT);

            for (Block b : link.getBlocks()) {
                Simulator.blockSimulateLoad(b, TYPE_DIRECT);
            }
        }

        //return ret;
    }

/*
    public String toString() {
        return "\nBlock:\n\tnumber = " + number +
               "\n\tbytes = " + loadDirect() +
               "\n";
    }
*/

    public int getBlockNumber(int i) {
        return ((bytes[i * 2] << 8)) | bytes[i * 2 + 1];
    }

    public void setBlockNumber(int i, int n) {
        bytes[i * 2]     = (byte)(n >> 8);
        bytes[i * 2 + 1] = (byte)(n & 0xFF); 
    }

    public Block getBlock(int i) {
        return (Block)(Globals.FS.getSector(getBlockNumber(i)));
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> ret = new ArrayList<Block>();
        ret.add(this);

        for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
            if (getBlockNumber(i) != 0) {
                ret.add(getBlock(i));
            }
        }

        return ret;
    }

    public ArrayList<Block> getDoubleBlocks() {
        ArrayList<Block> ret   = new ArrayList<Block>();
        //ArrayList<Block> links = getBlocks();

        ret.add(this);

        //for (Block link : links) {
        for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
            if (getBlockNumber(i) != 0) {
                Block link = getBlock(i);
                ret.add(link);

                for (int j = 0; j < Inode.LINKS_PER_BLOCK; j++) {
                    if (link.getBlockNumber(j) != 0) {
                        ret.add(link.getBlock(j));
                    }
                }
            }
        }

        return ret;
    }
}
