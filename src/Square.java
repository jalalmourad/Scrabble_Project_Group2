/**
 * Square class of Scrabble Game
 */
public class Square {
    private char letter;
    private boolean isEmpty;
    int score;

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

    public int getScore() {
        return score;
    }


    public int letterScore(char l) {
        if (l == 'A' || l == 'E' || l == 'I' || l == 'O' || l == 'U'
                || l == 'L' || l == 'N' || l == 'S' || l == 'T' || l == 'R') {
            score = 1;
        }
        else if (l == 'D' || l == 'G') {
            score = 2;
        }
        else if (l == 'B' || l == 'C' || l == 'M' || l == 'P') {
            score = 3;
        }
        else if (l == 'F' || l == 'H' || l == 'V' || l == 'W' || l == 'Y') {
            score = 4;
        }
        else if (l == 'K') {
            score = 5;
        }
        else if (l == 'J' || l == 'X') {
            score = 8;
        }
        else if (l == 'Q' || l == 'Z') {
            score = 10;
        }
        else { // Blank tile
            score = 0;
        }
        return score;
    }
}
