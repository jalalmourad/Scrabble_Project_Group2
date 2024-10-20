import java.util.*;

/**

 Player(s) class of Scrabble Game*/
public class Player {
    private String name;
    private int score;
    ArrayList<String> words;
    private Hand hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;

        this.words = new ArrayList<>();
        this.hand = new Hand();
    }


    public void calculateWordScore(String word) {
        int wordScore = 0;

        for (char letter : word.toCharArray()) {
            Square square = new Square(letter);
            wordScore += square.letterScore(letter);
        }

        this.score += wordScore;

        this.words.add(word);
    }

    public Hand getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerScore() {
        return score;
    }

    public static void main(String[] args) {
        String s = "HELLO";
        Player player1 = new Player("Jalal");

        player1.calculateWordScore(s);
        System.out.println(player1.getPlayerScore());
    }

}