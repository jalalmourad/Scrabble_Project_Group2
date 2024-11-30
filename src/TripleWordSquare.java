import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, triples the score of the word placed using this square
 */
public class TripleWordSquare extends Square implements Serializable {

    // x,y coordinates of the premium triple word squares found on the board
    public static int[][] twsCoords = {{0,0},{0,7},{0,14},
            {7,0},{7,14},
            {14,0},{14,7},{14,14}};

    public TripleWordSquare(char letter) {
        super(letter);
    }
}
