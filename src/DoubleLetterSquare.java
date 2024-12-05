import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, doubles the score of the letter placed
 */
public class DoubleLetterSquare extends Square implements Serializable {

    // x,y coordinates of the premium double letter squares found on the Normal board
    public static int[][] dlsCoordsNormal = {{0,3},{0,11},
            {2,6},{2,8},
            {3,0},{3,7},{3,14},
            {6,2},{6,6},{6,8},{6,12},
            {7,3},{7,11},
            {8,2},{8,6},{8,8},{8,12},
            {11,0},{11,7},{11,14},
            {12,6},{12,8},
            {14,3},{14,11}};

    // x,y coordinates of Custom Board 1: Fun Mode
    public static int[][] dlsCoordsFunMode = {{2,6},{2,8},{4,6},{4,8},{3,3},{3,11},
            {6,2},{8,2},{6,4},{8,4},{5,5},{9,5},
            {10,6},{10,8},{12,6},{12,8},{11,3},{11,11},
            {6,10},{6,12},{8,10},{8,12},{5,9},{9,9}};

    // x,y coordinates of Custom Board 2: Spiral Mode
    public static int[][] dlsCoordsSpiralMode = {{2,1},{1,2},{13,12},{12,13},{7,5},{7,6},
            {5,7},{5,8},{8,9},{9,9},{9,4},{9,5},
            {5,3},{6,3},{3,5},{3,6},{3,10},{3,11},
            {7,11},{8,11},{11,9},{11,10},{11,5},{11,6}};

    public DoubleLetterSquare(char letter) {
        super(letter);
    }

    /**
     * Same method as its super class except doubles the score returned from the letter
     */
    @Override
    public int letterScore(char l) {
        int score = super.letterScore(l);
        return score * 2;
    }
}
