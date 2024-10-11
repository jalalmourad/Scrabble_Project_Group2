/**
 * Board class of Scrabble Game
 */
public class Board {
    private static final int SIZE = 15;
    private Square[][] board = new Square[SIZE][SIZE];

    /**
     * Create empty board.
     */
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = new Square(' ');
            }
        }
    }

    /**
     * Print board, for display purposes.
     */
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) { // y-coordinates
            System.out.print("-------------------------------------------------------------\n");
            for (int j = 0; j < SIZE; j++) { // x-coordinates
                System.out.print("| " + board[i][j].getLetter() + " ");
            }
            System.out.println("|");
        }
        System.out.print("-------------------------------------------------------------\n");
    }

    /**
     * just for testing
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
    }
}
