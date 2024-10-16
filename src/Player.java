import java.util.*;

/**
 * Player(s) class of Scrabble Game
 */
public class Player {
    private String name;
    private int score;
    ArrayList<String> words;


    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.words = new ArrayList();
    }


    public int calculateWordScore(String word) {
        int wordScore = 0;

        for (char letter : word.toCharArray()) {
            Square square = new Square(letter);
            wordScore += square.letterScore();    }

        this.score += wordScore;

        this.words.add(word);
        return wordScore;
    }

    public int getPlayerScore() {
        return score;
    }

    public int getTotalScore(){
        return score;
    }

    public static void main(String[] args) {
        String s = "HELLO";
        Player player1 = new Player("Jalal");

        int i = player1.calculateWordScore(s);
        System.out.println(i);
    }

}
