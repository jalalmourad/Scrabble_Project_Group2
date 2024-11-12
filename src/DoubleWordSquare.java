
/**
 * Premium Square class of Scrabble Game, doubles the score of the word placed using this square
 */
public class DoubleWordSquare extends Square {

    // x,y coordinates of the premium double word squares found on the board
    public static int[][] dwsCoords = {{1,1},{1,13},
            {2,2},{2,12},
            {3,3},{3,11},
            {4,4},{4,10},
            {10,4},{10,10},
            {11,3},{11,11},
            {12,2},{12,12},
            {13,1},{13,13}};

    public DoubleWordSquare(char letter) {
        super(letter);
    }
}
