import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, doubles the score of the letter placed
 */
public class DoubleLetterSquare extends Square implements Serializable {

    // x,y coordinates of the premium double letter squares found on the board
    public static int[][] dlsCoords = {{0,3},{0,11},
            {2,6},{2,8},
            {3,0},{3,7},{3,14},
            {6,2},{6,6},{6,8},{6,12},
            {7,3},{7,11},
            {8,2},{8,6},{8,8},{8,12},
            {11,0},{11,7},{11,14},
            {12,6},{12,8},
            {14,3},{14,11}};

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
