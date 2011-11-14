import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class GridSquare implements Cloneable {
    JButton button;

    private int row;
    private int col;
    private int boardWidth;
    private int player;

    public GridSquare(int r, int c, int boardSize) {
        row = r;
        col = c;
        boardWidth = boardSize;

        button = new JButton();

        if (row == 0)                   setPlayer(Constants.PLAYER2);
        else if (row == boardWidth - 1) setPlayer(Constants.PLAYER1);
        else                            setPlayer(Constants.EMPTY);

        updateColor();
    }

    public GridSquare(GridSquare oldSquare) {
        this(oldSquare.getRow(), oldSquare.getCol(), oldSquare.getBoardWidth());   
    }

    public void setPlayer(int p) {
        player = p;
        updateColor();
    }

    private void updateColor() {
        if (player == Constants.EMPTY) button.setBackground(Constants.WHITE);
        else if (player == Constants.PLAYER1) button.setBackground(Constants.BLUE);
        else button.setBackground(Constants.RED);
    }

    public JButton getButton()     { return button;     }
    public int     getPlayer()     { return player;     }
    public int     getRow()        { return row;        }
    public int     getCol()        { return col;        }
    public int     getBoardWidth() { return boardWidth; }

    public void setButton(JButton newButton) { button = newButton; }
    public void setRow   (int newRow)        { row = newRow;       }
    public void setCol   (int newCol)        { col = newCol;       }

    // move if possible, and return whether it was successful
    public boolean move(GridSquare other) {
        if (canTake(other)) {
            return take(other);
        } else if (canMove(other)) {
            other.setPlayer(player);
            setPlayer(Constants.EMPTY);
            return true;
        }
        return false;
    }

    public boolean canMove(GridSquare other) {
        if (player == Constants.PLAYER1) {
            return other.getPlayer() == Constants.EMPTY &&
                   col == other.getCol()  &&
                   row == other.getRow() + 1;
        } else if (player == Constants.PLAYER2) {
            return other.getPlayer() == Constants.EMPTY &&
                   col == other.getCol()  &&
                   row == other.getRow() - 1;
        } 
        return false;
    }

    // take if possible, and return whether it was successful
    public boolean take(GridSquare other) {
        if (canTake(other)) {
            other.setPlayer(player);
            setPlayer(Constants.EMPTY);
            return true;
        }
        return false;
    }

    public boolean canTake(GridSquare other) {
        if (player == Constants.PLAYER1) {
            return other.getPlayer() == Constants.PLAYER2 &&
                   (col == other.getCol() - 1 || col == other.getCol() + 1) &&
                   row == other.getRow() + 1;
        }
        else if (player == Constants.PLAYER2) {
            return other.getPlayer() == Constants.PLAYER1 &&
                   (col == other.getCol() - 1 || col == other.getCol() + 1) &&
                   row == other.getRow() - 1;
        }
        return false;
    }

    public GridSquare clone() {
        GridSquare ret = new GridSquare(row, col, boardWidth);
        ret.setPlayer(player);

        return ret;
    }

    public boolean equals(Object other) {
        return row    == ((GridSquare)other).getRow() &&
               col    == ((GridSquare)other).getCol() &&
               player == ((GridSquare)other).getPlayer();
    }

}
