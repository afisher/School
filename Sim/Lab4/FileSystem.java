import java.util.ArrayList;
import javax.swing.*;

public class FileSystem {
    public static final int NUM_SECTORS = 8;
    public static final int NUM_INODES = 4;
    public static final int NUM_BLOCKS = NUM_SECTORS - NUM_INODES;

    private ArrayList<Inode> inodeFreeList = new ArrayList<Inode>();
    private ArrayList<Block> blockFreeList = new ArrayList<Block>();
    private ArrayList<File>  fileList      = new ArrayList<File>();

    private Sector[] sectors = new Sector[NUM_SECTORS + 1]; // position 0 is unused

    public FileSystem() {
        // initialize all of the inodes
        for (int i = 1; i <= NUM_INODES; i++) {
            sectors[i] = new Inode(i);
            inodeFreeList.add((Inode)sectors[i]);
        }

        // initialize all of the blocks
        for (int i = NUM_INODES + 1; i <= NUM_SECTORS; i++) {
            sectors[i] = new Block(i);
            blockFreeList.add((Block)sectors[i]);
        }
    }

    public Inode allocateInode() {
        if (inodeFreeList.isEmpty()) {
            Globals.complain("All out of inodes!");
            return null;
        }

        return inodeFreeList.remove(0);
    }

    public Block allocateBlock() {
        if (blockFreeList.isEmpty()) {
            Globals.complain("All out of blocks!");
            return null;
        }

        return blockFreeList.remove(0);
    }

    public String load() {
        String filename = getFilename();

        Inode inode = null;
        for (File f : fileList) {
            if (f.getName().equals(filename)) {
                inode = f.getInode();
            }
        }

        if (inode != null) return inode.load();
        else return "bad file name: " + filename;
    }

    public void save(String filename, String data) {
        if (!willFit(data)) {
            Globals.complain("There's not enough free space!");
            return;
        }

        //String filename = getFilename();
        Inode inode = allocateInode();

        if (inode != null) {
            inode.store(data);
            fileList.add(new File(filename, inode));
        }
    }

    public void delete() {
        String filename = getFilename();

        File file = null;
        for (File f : fileList) {
            if (f.getName().equals(filename)) {
                file = f;
            }
        }

        if (file != null) {
            deleteFile(file);
            fileList.remove(file);
        } else Globals.complain("File doesn't exist!");
    }

    public void clear() {
        for (File f : fileList) {
            deleteFile(f);
        }

        fileList.clear();
    }

    private void deleteFile(File file) {
        if (file != null) {
            Inode inode = file.getInode();
            inodeFreeList.add(inode);

            // free the direct link
            blockFreeList.add(inode.getDirectLink());

            // free the indirect link and its blocks
            Block singleIndirect = inode.getIndirectLink();
            if (singleIndirect != null) {
                for (Block b : singleIndirect.getBlocks()) {
                    blockFreeList.add(b);
                }
            }

            // free the double indirect link and its blocks
            Block doubleIndirect = inode.getDoubleIndirectLink();
            if (doubleIndirect != null) {
                for (Block b : doubleIndirect.getDoubleBlocks()) {
                    blockFreeList.add(b);
                }
            }

            file.clear();
        }
    }

    private String getFilename() {
        String s = (String) JOptionPane.showInputDialog(
                    new JFrame(),
                    "Enter filename",
                    "File name, please!",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "foo");

        if (s != null && s.length() > 0) return s;
        else return "foo";
    }

    public Sector getSector(int i) {
        return sectors[i];
    }

    public boolean willFit(String data) {
        if (blockFreeList.isEmpty()) return false;

        int length = data.length();
        int blocksNeeded = 1;
        
        int compareSize = Block.BLOCK_LENGTH;

        if (length > compareSize) {
            blocksNeeded += 2;
            compareSize += Block.BLOCK_LENGTH;

            for (int i = 1; i < Inode.LINKS_PER_BLOCK; i++) {
                if (length > compareSize) blocksNeeded++;
                compareSize += Block.BLOCK_LENGTH;
            }

            for (int i = 0; i < Inode.LINKS_PER_BLOCK; i++) {
                if (length > compareSize) {
                    blocksNeeded += 1;

                    for (int j = 0; j < Inode.LINKS_PER_BLOCK; j++) {
                        blocksNeeded += 1;
                        if (length > compareSize) blocksNeeded++;
                        compareSize += Block.BLOCK_LENGTH;
                    }
                }
            }
        }

        if (blocksNeeded > blockFreeList.size()) return false;
        return true;
    }

    public String toString() {
        return "FileSystem:\nFree inodes: \tsize = " + inodeFreeList.size() +
               "\n" + inodeFreeList.toString() +
               "\nFree blocks: \tsize =  " + blockFreeList.size() +
               "\n" + blockFreeList.toString() +
               "\nThe files: \tsize = " + fileList.size() +
               "\n" + fileList.toString();
    }
}
