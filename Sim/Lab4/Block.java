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
        // TODO impement
        return "Write me";
    }

    public String loadDoubleIndirect() {
        // TODO impement
        return "Write me";
    }

    public String toString() {
        return "\nBlock:\n\tnumber = " + number +
               "\n\tbytes = " + loadDirect() +
               "\n";
    }
}
