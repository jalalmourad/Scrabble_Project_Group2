import java.io.Serializable;

/**
 * Premium Square class of Scrabble Game, doubles the score of the word placed using this square
 */
public class DoubleWordSquare extends Square implements Serializable {

    // x,y coordinates of the premium double word squares found on the Normal board
    public static int[][] dwsCoordsNormal = {{1,1},{1,13},
            {2,2},{2,12},
            {3,3},{3,11},
            {4,4},{4,10},
            {10,4},{10,10},
            {11,3},{11,11},
            {12,2},{12,12},
            {13,1},{13,13}};

    // x,y coordinates of Custom Board 1: Fun Mode
    public static int[][] dwsCoordsFunMode = {{4,1},{7,1},{10,1},
            {1,4},{4,4},{10,4},{13,4},
            {1,7},{13,7},
            {1,10},{4,10},{10,10},{13,10},
            {4,13},{7,13},{10,13}};

    // x,y coordinates of Custom Board 2: Spiral Mode
    public static int[][] dwsCoordsSpiralMode = {{1,1},{12,1},{13,2},{1,12},
            {2,13},{13,13},{6,5},{5,9},
            {9,8},{9,3},{4,3},{3,7},
            {4,11},{9,11},{11,8},{11,4}};

    public DoubleWordSquare(char letter) {
        super(letter);
    }
}
