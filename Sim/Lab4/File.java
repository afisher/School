public class File {
    private String name;
    private Inode inode;

    public File(String n, Inode i) {
        name  = n;
        inode = i;
    }

    public String getName() { return name;  }
    public Inode getInode() { return inode; }

    public String toString() {
        return "File:\n\tname = " + name +
               "\n\tinode = " + inode;
    }

    public void clear() {
        inode.clear();
    }
}
