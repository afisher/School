import java.util.ArrayList;
import javax.swing.*;

public class FileSystem {
    public static final int NUM_SECTORS = 8;
    public static final int NUM_INODES = 4;
    public static final int NUM_BLOCKS = NUM_SECTORS - NUM_INODES;

    ArrayList<Inode> inodeFreeList = new ArrayList<Inode>();
    ArrayList<Block> blockFreeList = new ArrayList<Block>();
    ArrayList<File>  fileList      = new ArrayList<File>();

    Sector[] sectors = new Sector[NUM_SECTORS];

    public FileSystem() {
        // initialize all of the inodes
        for (int i = 0; i < NUM_INODES; i++) {
            sectors[i] = new Inode(i);
            inodeFreeList.add((Inode)sectors[i]);
        }

        // initialize all of the blocks
        for (int i = NUM_INODES; i < NUM_SECTORS; i++) {
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

    public void save(String s) {
        String filename = getFilename();
        Inode inode = allocateInode();

        inode.store(s);
        fileList.add(new File(filename, inode));
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

    public String toString() {
        return "FileSystem:\nFree inodes: " + inodeFreeList.toString() +
               "\nFree blocks: " + blockFreeList.toString() +
               "\nThe files: " + fileList.toString();
    }
}
