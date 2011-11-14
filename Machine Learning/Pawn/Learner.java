import java.util.*;

/**
 *
 * @author Ashley Fisher
 */
public class Learner {
    public static Collection<Board> boardsToAvoid = new ArrayList<Board>();

    public static void add(Board board) {
        if (!contains(board)) boardsToAvoid.add(board);
    }

    // checks to see if there is a board equivalent to the specified board
    // this is here because ArrayList's contains() wasn't using my version of equals()!
    public static boolean contains(Board board) {
        /*if (board != null && boardsToAvoid != null) {
            for (Board b : boardsToAvoid) {
                if (b != null && b.equals(board)) return true;
            }
        }
        return false;*/
        return boardsToAvoid.contains(board);
    }

    public static void learn(Board board, ArrayList<GridSquare> squares) {
        ArrayList<Move> possibleMoves = board.allLegalMoves(squares);
        Board newBoard;

        ArrayList<Board> boardsToCheck = getResultingBoards(board, possibleMoves);
        if (allContained(boardsToCheck) && board.getLastBoard() != null) {
            add(board.getLastBoard());
        }
    }

    public static ArrayList<Board> getResultingBoards(Board board, ArrayList<Move> moves) {
        // check all of the possible moves
        // if all of them lead to boards to avoid,
        // then add the last board to the boards to avoid too
        ArrayList<Board> boardsToCheck = new ArrayList<Board>();
        Board newBoard;
        for (Move move : moves) {
            newBoard = board.clone();
            newBoard.makeMove(move);
            boardsToCheck.add(newBoard);
        }
        return boardsToCheck;
    }

    // returns true if all of the boards are contained in the boards to avoid
    public static boolean allContained(ArrayList<Board> boards) {
        int sum = 0;
        for (Board b : boards) {
            if (contains(b)) sum++;
        }
        if (sum == boards.size()) {
            return true;
        }
        return false;
    }
}
