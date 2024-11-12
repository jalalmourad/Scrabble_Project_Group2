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
                wordScore += new DoubleLetterSquare(word.charAt(i)).letterScore(word.charAt(i));
                //System.out.println(squareTypes.get(i) + " " + word.charAt(i) + " " + square.letterScore(word.charAt(i)));

            } else if (squareTypes.get(i).equals("TripleLetterSquare")) {
                wordScore += new TripleLetterSquare(word.charAt(i)).letterScore(word.charAt(i));
                //System.out.println(squareTypes.get(i) + " " + word.charAt(i) + " " + square.letterScore(word.charAt(i)));

            } else if (squareTypes.get(i).equals("DoubleWordSquare")) {
                wordScore += new DoubleWordSquare(word.charAt(i)).letterScore(word.charAt(i));
                //System.out.println(squareTypes.get(i) + " " + word.charAt(i) + " " + square.letterScore(word.charAt(i)));

            } else if (squareTypes.get(i).equals("TripleWordSquare")) {
                wordScore += new TripleWordSquare(word.charAt(i)).letterScore(word.charAt(i));
                //System.out.println(squareTypes.get(i) + " " + word.charAt(i) + " " + square.letterScore(word.charAt(i)));

            } else {
                wordScore += new Square(word.charAt(i)).letterScore(word.charAt(i));
                //System.out.println(squareTypes.get(i) + " " + word.charAt(i) + " " + square.letterScore(word.charAt(i)));
            }
        }
        if (squareTypes.contains("DoubleWordSquare")) {
            wordScore *= 2;
            this.score += wordScore;
            this.words.add(word);
        } else if (squareTypes.contains("TripleWordSquare")) {
            wordScore *= 3;
            this.score += wordScore;
            this.words.add(word);
        } else {
            this.score += wordScore;
            this.words.add(word);
        }
        //System.out.println("Word Score: " + wordScore);
        //System.out.println("Player Score: " + this.score);
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