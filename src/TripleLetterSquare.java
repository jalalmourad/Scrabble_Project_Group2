import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, triples the score of the letter placed
 */
public class TripleLetterSquare extends Square implements Serializable {

    // x,y coordinates of the premium triple letter squares found on the Normal board
    public static int[][] tlsCoordsNormal = {{1,5},{1,9},
            {5,1},{5,5},{5,9},{5,13},
            {9,1},{9,5},{9,9},{9,13},
            {13,5},{13,9}};

    // x,y coordinates of Custom Board 1: Fun Mode
    public static int[][] tlsCoordsFunMode = {{5,3},{7,3},{9,3},
            {3,5},{3,7},{3,9},
            {11,5},{11,7},{11,9},
            {5,11},{7,11},{9,11}};

    // x,y coordinates of Custom Board 2: Spiral Mode
    public static int[][] tlsCoordsSpiralMode = {{13,1},{1,13},{5,5},
            {6,9},{9,7},{8,3},
            {3,3},{3,8},{5,11},
            {10,11},{11,7},{11,3}};

    public TripleLetterSquare(char letter) {
        super(letter);
    }

    /**
     * Same method as its super class except triples the score returned from the letter
     */
    @Override
    public int letterScore(char l) {
        int score = super.letterScore(l);
        return score * 3;
    }
}
