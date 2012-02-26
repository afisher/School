import javax.swing.*;

public class Globals {
    public static FileSystem FS;

    public static void init() {
        FS = new FileSystem();
    }

    public static void complain(String s) {
        JOptionPane.showMessageDialog(new JFrame(), 
                                                 s,
                                      "Complaint!",
                        JOptionPane.ERROR_MESSAGE);
    }
}
