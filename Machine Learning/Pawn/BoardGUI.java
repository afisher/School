import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class BoardGUI extends JFrame {
    private int boardWidth;
    private int buttonSize = 100;

    private Board board;

    private GridSquare lastSquare = null;
    private int curPlayer = 1;

    boolean gameOver = false;

    public BoardGUI(int size) {
        boardWidth = size;
        board = new Board(size);

        setPreferredSize(new Dimension(size*buttonSize, size*buttonSize));
        setTitle("Pawn Game");

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(size, size));

        GridSquare [][] squares = board.getSquares();
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                grid.add(squares[i][j].getButton());
            }
        }

        Container container = getContentPane();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                final int x = i;
                final int y = j;

                squares[x][y].getButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked(x, y);
                    }
                });
            }
        }

        container.add(grid);

        pack();
        setVisible(true);
    }

    private void buttonClicked(int i, int j) {
        if (gameOver) return;

        GridSquare curSquare = board.getSquares()[i][j];

        if (lastSquare != null && lastSquare.getPlayer() == Constants.PLAYER1) {
            Move move = new Move(lastSquare, curSquare);
            boolean success = board.makeMove(move);
            // if a successful move was made, check for winners and let the computer play
            if (success) {
                int winner = board.winner();
                if (winner != Constants.EMPTY) {
                    gameOver(winner);
                    return;
                }

                board.smartPlay(Constants.PLAYER2);

                winner = board.winner();
                if (winner != Constants.EMPTY) {
                    gameOver(winner);
                    return;
                }
            }
        }

        lastSquare = curSquare;
    }

    private void gameOver(int winner) {
        if (winner == Constants.PLAYER1 && board.getLastBoard() != null)
            Learner.add(board.getLastBoard());
        gameOver = true;

        Dialog dialog = new Dialog(this, "Message", "Player " + winner + " wins!");
    }

    public void exit() {
        this.dispose();
    }

}
