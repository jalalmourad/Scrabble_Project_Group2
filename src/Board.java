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
        System.out.print("     0   1   2   3   4   5   6   7   8   9  10  11  12  13  14\n");
        for (int i = 0; i < SIZE; i++) { // y-coordinates
            System.out.print("   -------------------------------------------------------------\n");
            if (i>=10) {
                System.out.print(i + " ");
            } else {System.out.print(i + "  ");}
            for (int j = 0; j < SIZE; j++) { // x-coordinates
                System.out.print("| " + board[i][j].getLetter() + " ");
            }
            System.out.println("|");
        }
        System.out.print("   -------------------------------------------------------------\n");
    }

    /**
     * Set's a letter on the board
     */
    public void setLetterOnBoard(int i, int j, char letter) {

        if (board[i][j].getLetter() == ' ') {
            board[i][j].setLetter(letter);
        } else {
            System.out.println("Invalid Insertion");
        }
    }


    /**
     * Get a letter on the board
     */
    public char getLetterOnBoard(int i, int j) {
        return board[i][j].getLetter();
    }



    /**
     * Main method used for testing
     */
    public static void main(String[] args) {
        Board b = new Board();
        b.printBoard();

    }
}
