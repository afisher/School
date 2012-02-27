import java.util.ArrayList;

public class Block extends Sector {
    public static final int BLOCK_LENGTH = 8;

    private byte[] bytes;

    public Block(int n) {
        super(n);
        bytes = new byte[BLOCK_LENGTH];
    }

    public void store(String s) {
        if (s.length() > BLOCK_LENGTH) {
            Globals.complain("That won't fit!");
        } else {
            for (int i = 0; i < s.length(); i++) {
                bytes[i] = (byte)(s.charAt(i));
            }
        }
    }

    public String loadDirect() {
        String ret = "";

        int i = 0;
        while (i < BLOCK_LENGTH && bytes[i] != 0) {
            ret += (char)(bytes[i]);
            i++;
        }

        return ret;
    }

    public String loadSingleIndirect() {
        String ret = "";

        for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
            Block currentBlock = (Block)(Globals.FS.getSector(getBlockNumber(i))); 
            ret += currentBlock.loadDirect();
        }

        return ret;
    }

    public String loadDoubleIndirect() {
        String ret = "";

        for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
            Block currentBlock = (Block)(Globals.FS.getSector(getBlockNumber(i)));
            ret += currentBlock.loadSingleIndirect();
        }

        return ret;
    }

    public String toString() {
        return "\nBlock:\n\tnumber = " + number +
               "\n\tbytes = " + loadDirect() +
               "\n";
    }

    public int getBlockNumber(int i) {
        return ((bytes[i * 2] << 8)) | bytes[i * 2 + 1];
    }

    public void setBlockNumber(int i, int n) {
        bytes[i * 2]     = (byte)(n >> 8);
        bytes[i * 2 + 1] = (byte)(n & 0xFF); 
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> ret = new ArrayList<Block>();

        for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
            ret.add((Block)(Globals.FS.getSector(getBlockNumber(i))));
        }

        return ret;
    }

    public ArrayList<Block> getDoubleBlocks() {
        ArrayList<Block> ret   = new ArrayList<Block>();
        ArrayList<Block> links = getBlocks();

        for (Block link : links) {
            ArrayList<Block> dataBlocks = link.getBlocks();
            for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
                ret.add(dataBlocks.get(i));
            }
        }

        return ret;
    }
}
