import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, triples the score of the letter placed
 */
public class TripleLetterSquare extends Square implements Serializable {

    // x,y coordinates of the premium triple letter squares found on the board
    public static int[][] tlsCoords = {{1,5},{1,9},
            {5,1},{5,5},{5,9},{5,13},
            {9,1},{9,5},{9,9},{9,13},
            {13,5},{13,9}};

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
