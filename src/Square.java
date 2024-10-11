/**
 * Square class of Scrabble Game
 */
public class Square {
    private char letter;
    private boolean isEmpty;

    public Square(char letter) {
        this.letter = letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }
}
