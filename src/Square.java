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


    public int letterScore() {
        for (char i = 'A'; i <= 'Z'; i++) {
            if (i == 'A' || i == 'E' || i == 'I' || i == 'O' || i == 'U'
                    || i == 'L' || i == 'N' || i == 'S' || i == 'T' || i == 'R') {
                score = 1;
            }
            else if (i == 'D' || i == 'G') {
                score = 2;
            }
            else if (i == 'B' || i == 'C' || i == 'M' || i == 'P') {
                score = 3;
            }
            else if (i == 'F' || i == 'H' || i == 'V' || i == 'W' || i == 'Y') {
                score = 4;
            }
            else if (i == 'K') {
                score = 5;
            }
            else if (i == 'J' || i == 'X') {
                score = 8;
            }
            else { // Q, Z
                score = 10;
            }
        }
        return score;
    }
}
