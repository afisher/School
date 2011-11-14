import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class Move {
    int row1;
    int col1;

    int row2;
    int col2;

    public Move(GridSquare s1, GridSquare s2) {
        row1 = s1.getRow();
        col1 = s1.getCol();

        row2 = s2.getRow();
        col2 = s2.getCol();
    }

    public int getRow1() { return row1; }
    public int getCol1() { return col1; }

    public int getRow2() { return row2; }
    public int getCol2() { return col2; }

    public String toString() {
        return "(" + row1 + ", " + col1 + ") to (" + row2 + ", " + col2 + ")";
    }
}
