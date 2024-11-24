import java.util.*;

/**
 Player(s) class of Scrabble Game
 */
public class Player {
    private String name;
    private int score;
    ArrayList<String> words;
    private Hand hand;
    private boolean isAI;

    public Player(String name) {
        this.name = name;
        this.score = 0;

        this.words = new ArrayList<>();
        this.hand = new Hand();
    }


    /**
     * Calculates the score of the word depending on the letters placed in each tile
     * COMPATIBLE FOR MILESTONE 3
     */
    public void calculateWordScore(String word, List<String> squareTypes) {
        int wordScore = 0;

        if (word.length() != squareTypes.size()) {
            throw new IllegalArgumentException("The number of square types must match the length of the word.");
        }

        for (int i = 0; i < squareTypes.size(); i++) {
            if (squareTypes.get(i).equals("DoubleLetterSquare")) {
                wordScore += new DoubleLetterSquare(word.charAt(i)).letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("TripleLetterSquare")) {
                wordScore += new TripleLetterSquare(word.charAt(i)).letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("DoubleWordSquare")) {
                wordScore += new DoubleWordSquare(word.charAt(i)).letterScore(word.charAt(i));

            } else if (squareTypes.get(i).equals("TripleWordSquare")) {
                wordScore += new TripleWordSquare(word.charAt(i)).letterScore(word.charAt(i));

            } else {
                wordScore += new Square(word.charAt(i)).letterScore(word.charAt(i));
            }
        }
        if (squareTypes.contains("DoubleWordSquare")) {
            int dwsCount = Collections.frequency(squareTypes, "DoubleWordSquare");
            wordScore *= 2 * dwsCount;
            this.score += wordScore;
            this.words.add(word);
        }
        if (squareTypes.contains("TripleWordSquare")) {
            int twsCount = Collections.frequency(squareTypes, "TripleWordSquare");
            wordScore *= 3 * twsCount;
            this.score += wordScore;
            this.words.add(word);
        }
        if (!(squareTypes.contains("DoubleWordSquare") || squareTypes.contains("TripleWordSquare"))) {
            this.score += wordScore;
            this.words.add(word);
        }
    }

    public int getWordScore(String word) {
        int wordScore = 0;
        for (char letter : word.toCharArray()) {
            Square square = new Square(letter);
            wordScore += square.letterScore(letter);
        }
        return wordScore;
    }

    public void removeLettersFromHand(String word) {
        for (char letter : word.toCharArray()) {
            getHand().removeLetter(letter);  // Remove the letter from the hand
        }
    }

    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }

    public boolean isAI() {
        return isAI;
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