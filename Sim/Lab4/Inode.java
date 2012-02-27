public class Inode extends Sector {
    private int size;
    private Block directLink;
    private Block indirectLink;
    private Block doubleIndirectLink;

    public static final int LINKS_PER_BLOCK = 4; 

    public Inode(int n) {
        super(n);
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
        directLink.store(data);
    }

    private void storeSingle(String data) {
        storeDirect(data.substring(0, Block.BLOCK_LENGTH));

        indirectLink = Globals.FS.allocateBlock();

        for (int i = 0; i < LINKS_PER_BLOCK; i++) {
            Block newBlock = Globals.FS.allocateBlock();

            int start = Block.BLOCK_LENGTH * (i+1);
            int end   = Math.min(data.length(), Block.BLOCK_LENGTH * (i+2));

            if (data.length() > start) {
                newBlock.store(data.substring(start, end));
            }

            indirectLink.setBlockNumber(i, newBlock.getNumber());
        }
    }

    private void storeDouble(String data) {
        // TODO implement
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

    private int singleSizeMax() {
        return Block.BLOCK_LENGTH + LINKS_PER_BLOCK * Block.BLOCK_LENGTH;
    }

    private int doubleSizeMax() {
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
               "\n\tdoubleIndirectLink = " + doubleIndirectLink +
               "\n";
    }

    public void clear() {
        size = 0;

        directLink.clearBytes();

        directLink         = null;
        indirectLink       = null;
        doubleIndirectLink = null;
    }
}
