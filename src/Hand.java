import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {

    private ArrayList<Character> letters;

    Bag bag;

    public Hand() {
        letters = new ArrayList<Character>();
        bag = new Bag();

        setLetters(bag.getAlphabet());
    }

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void addLetter(Character letter) {
        letters.add(letter);
    }

    /**
     * Refills the hand of the player with 7 letters
     */
    public void refillHand(){
        while (letters.size()<7){
            addLetter(bag.addRandomChar());
        }

    }

    public void setLetters(ArrayList<Character> bagChars){
        this.letters = bagChars;
    }

    public void removeLetter(Character letter) {
        letters.remove(letter);
    }


    /**
     *Prints the hand of the player
     */
    public void printHand(String name) {
        System.out.print(name + "'s letters in hand: ");
        for(int i = 0; i<letters.size();i++){

            System.out.print(letters.get(i)+" ");
        }
        System.out.println();
    }

    public boolean lettersInHand(char letter){
        return letters.contains(letter);
    }

    public int getLetterPosition(char c){
        return letters.indexOf(c);
    }

    public int getLettersSize(){
        return letters.size();
    }

    /**
     * Swap method to swap a letter from a players hand with another random letter
     */
    public void swap(char letter, Bag bag){

        if (lettersInHand(letter)){
            letters.set(getLetterPosition(letter), bag.addRandomChar());
        }

    }


}