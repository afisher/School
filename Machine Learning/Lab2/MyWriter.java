// A minimal, undocumented convenience class -- who wrote this anyways?
// Has the advantage (over a straight PrintWriter) of not needing try-catch blocks
// ... and will prompt the user for where to put the file... 

import java.io.*;
import java.awt.*;

public class MyWriter 
{
    protected PrintWriter ps;
    
    public MyWriter() {
        FileDialog fd = new FileDialog(new Frame(), "Where would you like it?", FileDialog.SAVE);
        fd.setFile("*.*");
        fd.show();
        String filename = fd.getDirectory() + fd.getFile();
        try {
            ps = new PrintWriter(new FileWriter(filename));
        } catch(Exception e) {System.out.print("PW1: No such file! " + e);}
    }
    
    public MyWriter(String f) {
        try {
            ps = new PrintWriter(new FileWriter(f));
        } catch(Exception e) {System.out.print("PW2: No such file! " + e);}
    }
    
    public void print(String s) {
        ps.print(s);
    }
    
    public void println(String s) {
        print(s+"\n");
    }
    
    public void close() {
        ps.close();
    }
        
}
