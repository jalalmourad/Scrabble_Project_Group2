import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, triples the score of the word placed using this square
 */
public class TripleWordSquare extends Square implements Serializable {

    // x,y coordinates of the premium triple word squares found on the Normal board
    public static int[][] twsCoordsNormal = {{0,0},{0,7},{0,14},
            {7,0},{7,14},
            {14,0},{14,7},{14,14}};

    // x,y coordinates of Custom Board 1: Fun Mode
    public static int[][] twsCoordsFunMode = {{6,6},{6,8},{8,6},{8,8},{7,5},{7,9},{5,7},{9,7}};

    // x,y coordinates of Custom Board 2: Spiral Mode
    public static int[][] twsCoordsSpiralMode= {{5,6},{7,9},{9,6},{7,3},{3,4},{3,9},{6,11},{11,11}};

    public TripleWordSquare(char letter) {
        super(letter);
    }
}
