import java.util.ArrayList;
import javax.swing.*;

public class FileSystem {
    public static final int NUM_SECTORS = 64; 
    public static final int NUM_INODES = 4;
    public static final int NUM_BLOCKS = NUM_SECTORS - NUM_INODES;

    private ArrayList<Inode> inodeFreeList = new ArrayList<Inode>();
    private ArrayList<Block> blockFreeList = new ArrayList<Block>();
    private ArrayList<File>  fileList      = new ArrayList<File>();

    private Sector[] sectors = new Sector[NUM_SECTORS];

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
        }

        return inodeFreeList.remove(0);
    }

    public Block allocateBlock() {
        if (blockFreeList.isEmpty()) {
            Globals.complain("All out of blocks!");
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

    public void delete() {
        String filename = getFilename();

        File file = null;
        for (File f : fileList) {
            if (f.getName().equals(filename)) {
                file = f;
            }
        }

        deleteFile(file);
        fileList.remove(file);
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

            // free the indirect link
            if (inode.getIndirectLink() != null) {
                blockFreeList.add(inode.getIndirectLink());
            }

            // free the double indirect link
            if (inode.getDoubleIndirectLink() != null) {
                blockFreeList.add(inode.getDoubleIndirectLink());
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

    public String toString() {
        return "FileSystem:\nFree inodes: " + inodeFreeList.toString() +
               "\nFree blocks: " + blockFreeList.toString() +
               "\nThe files: " + fileList.toString();
    }
}
