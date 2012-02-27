public class Inode extends Sector {
    private int size;
    private Block directLink;
    private Block indirectLink;
    private Block doubleIndirectLink;

    private static final int LINKS_PER_BLOCK = 4; 

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
        directLink = Globals.FS.allocateBlock();
        int lastIndex = Math.min(Block.BLOCK_LENGTH, data.length());
        directLink.store(data.substring(0, lastIndex));
    }

    private void storeSingle(String data) {
        //TODO implement
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
               "\n\tindirectLink = " + indirectLink +
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
