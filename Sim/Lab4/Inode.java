import java.util.ArrayList;

public class Inode extends Sector {
    private int size;
    private Block directLink;
    private Block indirectLink;
    private Block doubleIndirectLink;

    public static final int LINKS_PER_BLOCK = 4;

    public Inode(int n) {
        super(n);
    }

    public void startStore(String data) {
        if (data.length() > doubleSizeMax()) {
            Globals.complain("File too large");
            return;
        }

        simulateStore(data);
    }

    public void simulateStore(String data) {
        Simulator.inodeSimulateStore(this, data);
    }

    public void store(String data) {
        size = data.length();

        if (size <= Block.BLOCK_LENGTH) {
            storeDirect(data);
        } else if (size <= singleSizeMax()) {
            storeSingle(data);
        } else if (size <= doubleSizeMax()) {
            storeDouble(data);
        } else {
            Globals.complain("Too big! Max file size is: " + doubleSizeMax());
        }
    }

    private void storeDirect(String data) {
        if (directLink == null) {
            directLink = Globals.FS.allocateBlock();
        }
        //directLink.store(data);
        Simulator.blockSimulateStore(directLink, data, false);
    }

    private void storeSingle(String data) {
        storeDirect(data.substring(0, Block.BLOCK_LENGTH));

        indirectLink = Globals.FS.allocateBlock();

        ArrayList<Block> blocks = new ArrayList<Block>();
        String linkData = "";

        // figure out the bytes to give to the link
        for (int i = 0; i < LINKS_PER_BLOCK; i++) {
            int start = Block.BLOCK_LENGTH * (i+1);
            int end   = Math.min(data.length(), Block.BLOCK_LENGTH * (i+2));

            if (data.length() > start) {
                Block block = Globals.FS.allocateBlock();
                blocks.add(block);

                linkData += "" + block.getNumber();
            }
        }

        // simulate storing the indirect link
        Simulator.blockSimulateStore(indirectLink, linkData, true);

        // simulate storing the blocks
        for (int i = 0; i < LINKS_PER_BLOCK; i++) {
            int start = Block.BLOCK_LENGTH * (i+1);
            int end   = Math.min(data.length(), Block.BLOCK_LENGTH * (i+2));

            if (data.length() > start) {
                //newBlock.store(data.substring(start, end));
                Simulator.blockSimulateStore(blocks.get(i), data.substring(start, end), false);
            }
        }
    }

    private void storeDouble(String data) {
        storeSingle(data);

        int start = singleSizeMax();

        doubleIndirectLink = Globals.FS.allocateBlock();

        for (int i = 0; i < LINKS_PER_BLOCK; i++) {
            if (data.length() > start - 1) {
                Block newBlock = Globals.FS.allocateBlock();
                doubleIndirectLink.setBlockNumber(i, newBlock.getNumber());

                for (int j = 0; j < LINKS_PER_BLOCK; j++) {
                    if (data.length() > start - 1) {
                        Block dataBlock = Globals.FS.allocateBlock();

                        int end = Math.min(start + Block.BLOCK_LENGTH, data.length());
                        dataBlock.store(data.substring(start, end));
                        newBlock.setBlockNumber(j, dataBlock.getNumber());

                    } else {
                        newBlock.setBlockNumber(j, 0);
                    }

                    start += Block.BLOCK_LENGTH;
                }
            } else {
                doubleIndirectLink.setBlockNumber(i, 0);
            }
        }
    }

    public String load() {
        // there's always direct data
        String ret = loadDirect();

        // if there's more data, load single
        if (size > Block.BLOCK_LENGTH) {
            ret += loadSingleIndirect();
        }

        // if there's still more data, load double
        if (size > singleSizeMax()) {
            ret += loadDoubleIndirect();
        }

        return ret;
    }

    private String loadDirect() {
        return directLink.loadDirect();
    }

    private String loadSingleIndirect() {
        return indirectLink.loadSingleIndirect(); 
    }

    private String loadDoubleIndirect() {
        return doubleIndirectLink.loadDoubleIndirect();
    }

    public static int singleSizeMax() {
        return Block.BLOCK_LENGTH + LINKS_PER_BLOCK * Block.BLOCK_LENGTH;
    }

    public static int doubleSizeMax() {
        return singleSizeMax() + LINKS_PER_BLOCK * LINKS_PER_BLOCK * Block.BLOCK_LENGTH;
    }

    public int getSize() { return size; }

    public Block getDirectLink()         { return directLink;         }
    public Block getIndirectLink()       { return indirectLink;       }
    public Block getDoubleIndirectLink() { return doubleIndirectLink; }

    public String toString() {
        return "\nInode:\n\tnumber = " + number + 
               "\n\tsize = " + size + 
               "\n\tdirectLink = " + directLink +
               "\n\tindirectLink = " + (indirectLink == null? null : indirectLink.getBlocks()) +
               "\n\tdoubleIndirectLink = " + (doubleIndirectLink == null? null : doubleIndirectLink.getDoubleBlocks()) +
               "\n";
    }

    public void clear() {
        size = 0;

        directLink         = null;
        indirectLink       = null;
        doubleIndirectLink = null;
    }
}
