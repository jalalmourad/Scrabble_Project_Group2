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


    public int letterScore(){

        if (letter == 'A'){
            score = 1;
        }
        if (letter == 'E'){
            score = 1;
        }
        if (letter == 'I'){
            score = 1;
        }
        if (letter == 'O'){
            score = 1;
        }
        if (letter == 'U'){
            score = 1;
        }
        if (letter == 'L'){
            score = 1;
        }
        if (letter == 'N'){
            score = 1;
        }
        if (letter == 'S'){
            score = 1;
        }
        if (letter == 'T'){
            score = 1;
        }
        if (letter == 'R'){
            score = 1;
        }
        if (letter == 'D'){
            score = 2;
        }
        if (letter == 'G'){
            score = 2;
        }
        if (letter == 'B'){
            score = 3;
        }
        if (letter == 'C'){
            score = 3;
        }
        if (letter == 'M'){
            score = 3;
        }
        if (letter == 'P'){
            score = 3;
        }
        if (letter == 'F'){
            score = 3;
        }
        if (letter == 'H'){
            score = 4;
        }
        if (letter == 'V'){
            score = 4;
        }
        if (letter == 'W'){
            score = 4;
        }
        if (letter == 'Y'){
            score = 4;
        }
        if (letter == 'K'){
            score = 5;
        }
        if (letter == 'J'){
            score = 8;
        }
        if (letter == 'X'){
            score = 8;
        }
        if (letter == 'Q'){
            score = 10;
        }
        if (letter == 'Z'){
            score = 10;
        }
        return score;
    }


}
