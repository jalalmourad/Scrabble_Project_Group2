import java.util.*;

/**
 Player(s) class of Scrabble Game
 */
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

    /**
     *Calculates the score of the word depending on the letters placed in each tile
     */
    /**
    public void calculateWordScore(String word) {
        int wordScore = 0;
        for (char letter : word.toCharArray()) {
            Square square = new Square(letter);
            wordScore += square.letterScore(letter);
        }

        this.score += wordScore;
        this.words.add(word);
    }
     */

    /**
     * Calculates the score of the word depending on the letters placed in each tile
     * COMPATIBLE FOR MILESTONE 3
     */
    public void calculateWordScore(String word, List<String> squareTypes) {
        int wordScore = 0;

        for (int i = 0; i < squareTypes.size(); i++) {
            if (squareTypes.get(i).equals("DoubleLetterSquare")) {
                DoubleLetterSquare square = new DoubleLetterSquare(word.charAt(i));
                wordScore += square.letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("TripleLetterSquare")) {
                TripleLetterSquare square = new TripleLetterSquare(word.charAt(i));
                wordScore += square.letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("DoubleWordSquare")) {
                DoubleWordSquare square = new DoubleWordSquare(word.charAt(i));
                wordScore += square.letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("TripleWordSquare")) {
                TripleWordSquare square = new TripleWordSquare(word.charAt(i));
                wordScore += square.letterScore(word.charAt(i));

            } else {
                Square square = new Square(word.charAt(i));
                wordScore += square.letterScore(word.charAt(i));
            }
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


}