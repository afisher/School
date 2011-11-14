import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class Board implements Cloneable {
//    private ArrayList<GridSquare> squares;

    private GridSquare [][] squares;
    private int boardWidth;

    private int lastPlayer;
    private Board lastBoard; // the last board that resulted from a move made by player 2

    public Board(int boardSize) {
        boardWidth = boardSize;
        lastPlayer = Constants.EMPTY;

        squares = new GridSquare[boardWidth][boardWidth];

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                squares[i][j] = new GridSquare(i, j, boardWidth);
            }
        }
    }

    public void setSquares(GridSquare[][] newSquares) { squares = newSquares; }
    public void setLastPlayer(int newPlayer) { lastPlayer = newPlayer; }
    public void setLastBoard(Board newBoard) { lastBoard = newBoard; }

    public GridSquare[][] getSquares() { return squares; }
    public int getBoardWidth()  { return boardWidth; }
    public int getLastPlayer()  { return lastPlayer; }
    public Board getLastBoard() { return lastBoard; }

    // return an ArrayList containing all of the squares "in front"
    // of the square at row,col
    private ArrayList<GridSquare> adjacentSquares(int row, int col) {
        ArrayList<GridSquare> ret = new ArrayList<GridSquare>();
        GridSquare square = squares[row][col];

        if (square.getPlayer() == Constants.PLAYER1) {
            if (row > 0) {
                ret.add(squares[row - 1][col]);
                
                if (col > 0) {
                    ret.add(squares[row - 1][col - 1]);
                }

                if (col < boardWidth - 1) {
                    ret.add(squares[row - 1][col + 1]);
                }
            }
        } else if (square.getPlayer() == Constants.PLAYER2) {
            if (row < boardWidth - 1) {
                ret.add(squares[row + 1][col]);

                if (col > 0) {
                    ret.add(squares[row + 1][col - 1]);
                }

                if (col < boardWidth - 1) {
                    ret.add(squares[row + 1][col + 1]);
                }
            }
        }

        return ret;
    }

    // return all of the squares that belong to the specified player
    private ArrayList<GridSquare> playerPawns(int player) {
        ArrayList<GridSquare> ret = new ArrayList<GridSquare>();
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (squares[i][j].getPlayer() == player) {
                    ret.add(squares[i][j]);
                }
            }
        }
        return ret;
    }

    // return if there are any legal moves for square to make
    private boolean anyLegalMoves(GridSquare square) {
        ArrayList<GridSquare> adj = adjacentSquares(square.getRow(), square.getCol());
        for (GridSquare a : adj) {
            if (square.canMove(a) || square.canTake(a)) return true;
        }
        return false;
    }

    // return all possible moves for a specified square
    public ArrayList<Move> allLegalMoves(GridSquare square) {
        ArrayList<GridSquare> adj = adjacentSquares(square.getRow(), square.getCol());

        ArrayList<Move> ret = new ArrayList<Move>();

        for (GridSquare a : adj) {
            if (square.canMove(a) || square.canTake(a)) {
                ret.add(new Move(square, a));
            }
        }
        
        return ret;
    }

    public ArrayList<Move> allLegalMoves(ArrayList<GridSquare> squares) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        for (GridSquare s : squares) {
            ArrayList<Move> moves = allLegalMoves(s);
            for (Move m : moves) {
                possibleMoves.add(m);
            }
        }

        return possibleMoves;
    }
                

    // return which player has won the game -- the empty constant if nobody won
    public int winner() {
        ArrayList<GridSquare> pawns1 = playerPawns(Constants.PLAYER1);
        ArrayList<GridSquare> pawns2 = playerPawns(Constants.PLAYER2);

        // if a player has no pawns, the other player has won
        if (pawns1.isEmpty()) {
            return Constants.PLAYER2;
        }
        if (pawns2.isEmpty()) {
            return Constants.PLAYER1;
        }

        // if either player has advanced to the other side, they have won
        for (GridSquare p : pawns1) {
            if (p.getRow() == 0) return Constants.PLAYER1;
        }
        for (GridSquare p : pawns2) {
            if (p.getRow() == boardWidth - 1) return Constants.PLAYER2;
        }

        // if either player has legal moves (and the above cases weren't met), the game hasn't ended yet
        for (GridSquare p : pawns1) {
            if (anyLegalMoves(p)) return Constants.EMPTY;
        }

        for (GridSquare p : pawns2) {
            if (anyLegalMoves(p)) return Constants.EMPTY;
        }

        // if we got here, then the current player has no legal moves
        return lastPlayer;
    }

    public boolean makeMove(Move move) {
        GridSquare first = squares[move.getRow1()][move.getCol1()];
        GridSquare second = squares[move.getRow2()][move.getCol2()];

        int newPlayer = first.getPlayer();

        boolean success = first.move(second);

        if (success) { 
            lastPlayer = newPlayer;
            if (lastPlayer == Constants.PLAYER1 && lastBoard != null) {
                Learner.learn(this, playerPawns(Constants.PLAYER2));
            }

            // if the last player was the computer, update the last board
            if (lastPlayer == Constants.PLAYER2) {
                lastBoard = this.clone();
            }

        }

        return success;
    }

    // make a random move or take for specified player
    public boolean randomPlay(int player) {
        ArrayList<GridSquare> pawns = playerPawns(player);

        ArrayList<Move> possibleMoves = allLegalMoves(pawns);
        if (possibleMoves.isEmpty()) return false;
        
        Random randGen = new Random();

        ArrayList<Board> possibleBoards = Learner.getResultingBoards(this, possibleMoves);
        if (Learner.allContained(possibleBoards)) return false;
        else {
            int index = randGen.nextInt(possibleMoves.size());
            while (Learner.contains(possibleBoards.get(index))) {
                index = randGen.nextInt(possibleMoves.size());
            }

            return makeMove(possibleMoves.get(index));
        }

    }

    // make a "smart" move or take for specified player
    // meaning the player always takes if it can
    public boolean smartPlay(int player) {
        ArrayList<GridSquare> pawns = playerPawns(player);

        ArrayList<Move> possibleMoves = allLegalMoves(pawns);
        if (possibleMoves.isEmpty()) return false;
        
        ArrayList<Board> possibleBoards = Learner.getResultingBoards(this, possibleMoves);
        if (Learner.allContained(possibleBoards)) return false;
        else {
            int index = 0;
            while (Learner.contains(possibleBoards.get(index))) {
                index++;
            }

            return makeMove(possibleMoves.get(index));
        }
    }

    public Board clone() {
        Board ret = new Board(boardWidth);
        GridSquare [][] newSquares = new GridSquare[boardWidth][boardWidth];

        ret.setLastPlayer(lastPlayer);
        if (lastBoard != null) ret.setLastBoard(lastBoard.clone());
        
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                newSquares[i][j] = squares[i][j].clone();
            }
        }

        ret.setSquares(newSquares);

        return ret;
    }

    public boolean equals(Object other) {
        if (boardWidth != ((Board)other).getBoardWidth()) return false;

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (!squares[i][j].equals(((Board)other).getSquares()[i][j])) return false;
            }
        }

        return true;
    }
}

