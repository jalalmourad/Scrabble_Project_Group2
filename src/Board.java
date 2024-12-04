import java.io.Serializable;

/**
 * Board class of Scrabble Game
 */
public class Board implements Serializable {
    private static final int SIZE = 15;
    private Square[][] board = new Square[SIZE][SIZE];

    // Different Board Options
    public static int[][][] premiumTilesNormal = {DoubleLetterSquare.dlsCoordsNormal, TripleLetterSquare.tlsCoordsNormal, DoubleWordSquare.dwsCoordsNormal, TripleWordSquare.twsCoordsNormal};
    public static int[][][] premiumTilesFunMode = {DoubleLetterSquare.dlsCoordsFunMode, TripleLetterSquare.tlsCoordsFunMode, DoubleWordSquare.dwsCoordsFunMode, TripleWordSquare.twsCoordsFunMode};
    public static int[][][] premiumTilesSpiralMode = {DoubleLetterSquare.dlsCoordsSpiralMode, TripleLetterSquare.tlsCoordsSpiralMode, DoubleWordSquare.dwsCoordsSpiralMode, TripleWordSquare.twsCoordsSpiralMode};

    /**
     * Create empty board.
     */
    public Board(String selection) {
        switch (selection) {
            case "Normal Mode" -> NormalModeBoard();
            case "Fun Mode" -> FunModeBoard();
            case "Spiral Mode" -> SpiralModeBoard();
            case "Boring Mode" -> BoringModeBoard();
        }


        /**
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : premiumTilesNormal) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsNormal) {
                                board[i][j] = new DoubleLetterSquare(' '); // Create Double Letter Square
                            } else if (premiumType == TripleLetterSquare.tlsCoordsNormal) {
                                board[i][j] = new TripleLetterSquare(' '); // Create Triple Letter Square
                            } else if (premiumType == DoubleWordSquare.dwsCoordsNormal) {
                                board[i][j] = new DoubleWordSquare(' '); // Create Double Word Square
                            } else if (premiumType == TripleWordSquare.twsCoordsNormal) {
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
         */
    }

    /**
     * Create Normal Mode Board
     */
    public void NormalModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : premiumTilesNormal) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsNormal) {
                                board[i][j] = new DoubleLetterSquare(' '); // Create Double Letter Square
                            } else if (premiumType == TripleLetterSquare.tlsCoordsNormal) {
                                board[i][j] = new TripleLetterSquare(' '); // Create Triple Letter Square
                            } else if (premiumType == DoubleWordSquare.dwsCoordsNormal) {
                                board[i][j] = new DoubleWordSquare(' '); // Create Double Word Square
                            } else if (premiumType == TripleWordSquare.twsCoordsNormal) {
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
     * Create Fun Mode Board (Custom 1)
     * New format of Premium Tiles
     */
    public void FunModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : premiumTilesFunMode) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsFunMode) {
                                board[i][j] = new DoubleLetterSquare(' '); // Create Double Letter Square
                            } else if (premiumType == TripleLetterSquare.tlsCoordsFunMode) {
                                board[i][j] = new TripleLetterSquare(' '); // Create Triple Letter Square
                            } else if (premiumType == DoubleWordSquare.dwsCoordsFunMode) {
                                board[i][j] = new DoubleWordSquare(' '); // Create Double Word Square
                            } else if (premiumType == TripleWordSquare.twsCoordsFunMode) {
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
     * Create Spiral Mode Board (Custom 2)
     * Premium Tiles in Spiral Format
     */
    public void SpiralModeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) { // Looping through board
                boolean isPremium = false;

                for (int[][] premiumType : premiumTilesSpiralMode) { // Looping through premium tile types
                    for (int[] premiumCoords : premiumType) { // Looping through individual premium tile coordinates of each type
                        if (premiumCoords[1] == i && premiumCoords[0] == j) {
                            if (premiumType == DoubleLetterSquare.dlsCoordsSpiralMode) {
                                board[i][j] = new DoubleLetterSquare(' '); // Create Double Letter Square
                            } else if (premiumType == TripleLetterSquare.tlsCoordsSpiralMode) {
                                board[i][j] = new TripleLetterSquare(' '); // Create Triple Letter Square
                            } else if (premiumType == DoubleWordSquare.dwsCoordsSpiralMode) {
                                board[i][j] = new DoubleWordSquare(' '); // Create Double Word Square
                            } else if (premiumType == TripleWordSquare.twsCoordsSpiralMode) {
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
     * Create Boring Mode Board (Custom 3)
     * All squares are normal, no Premium Tiles
     */
    public void BoringModeBoard() {
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
