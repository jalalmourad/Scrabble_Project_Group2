/**
 * Board class of Scrabble Game
 */
public class Board {
    private static final int SIZE = 15;
    private Square[][] board = new Square[SIZE][SIZE];
    public static int[][][] premiumTiles = {DoubleLetterSquare.dlsCoords, TripleLetterSquare.tlsCoords, DoubleWordSquare.dwsCoords, TripleWordSquare.twsCoords};

    /**
     * Create empty board.
     */
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : premiumTiles) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoords) {
                                board[i][j] = new DoubleLetterSquare(' '); // Create Double Letter Square
                            } else if (premiumType == TripleLetterSquare.tlsCoords) {
                                board[i][j] = new TripleLetterSquare(' '); // Create Triple Letter Square
                            } else if (premiumType == DoubleWordSquare.dwsCoords) {
                                board[i][j] = new DoubleWordSquare(' '); // Create Double Word Square
                            } else if (premiumType == TripleWordSquare.twsCoords) {
                                board[i][j] = new TripleWordSquare(' '); // Create Triple Word Square
                            }
                            isPremium = true;
                            break;
                        }
                    }
                    if (isPremium) {
                        break;
                    }
                }
                if (!isPremium) {
                    board[i][j] = new Square(' '); // Create normal Square
                }
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
     *Used to remove Invalid words
     */
    public void setDeleteLetterFromBoard(int i, int j, char letter) {
            board[i][j].setLetter(letter);
    }


    /**
     * Get a letter on the board
     */
    public char getLetterOnBoard(int i, int j) {
        return board[i][j].getLetter();
    }

    /**
     * Get type of square, premium or normal
     */
    public String getSquareType(int i, int j) {
        return board[i][j].getClass().getName();
    }
}
