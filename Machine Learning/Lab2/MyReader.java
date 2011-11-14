/*
 * MyReader.java
 * A wrapper for a a BufferedReader including a FileDialog for easy file opening...
 * Created on September 10, 2003, and modified intermittently thereafter
 * @author  jrl
 */

import java.io.*;
import java.awt.*;
import java.net.*;
import java.applet.*;

public class MyReader {
    
    BufferedReader br;
    String filename = "nothing, yet";

    public String getFilename() {return filename;}      // So you can find out what the pathname was later
    
    public MyReader() {                                 // default, prompts user for file
        openIt(getFileName());    
    }
    
    public MyReader(String filename) {                  // opens the file passed
        openIt(filename);
    }
    
    public MyReader(String filename, Applet theApplet) { // opens a file from an Applet!
        try {
            URL theURL = new URL(theApplet.getDocumentBase(), filename);
            InputStreamReader isr = new InputStreamReader(theURL.openStream());
            br = new BufferedReader(isr);
        } catch (Exception e) {System.out.println("MyReader -- bad file from net" + e);}
    }
    
    private void openIt (String filename) {
	this.filename = filename;       // save the path so you can discover it later (if you want to)
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {System.out.println("MyReader -- can't open " + filename + "!" + e);}
    }
    
    public String giveMeTheNextLine() {
        try {
            return br.readLine();
        } catch (Exception e) {System.out.println("MyReader -- eof?!" + e);}
        return "";
    }
    
    public boolean hasMoreData() {
        try {
            return br.ready();
        } catch (Exception e) {System.out.println("MyReader -- disaster!" + e);}
        return false;
    }
    
    public void close() {
        try {
            br.close();
        } catch (Exception e) {System.out.println("MyReader -- can't close that!" + e);}
    }
    
    private String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Input File");
        fd.setFile("input");
        fd.show();
        return fd.getDirectory()+fd.getFile();  // return the complete path
    }
    
}
