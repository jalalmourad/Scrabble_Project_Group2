import java.util.ArrayList;
import java.util.Random;

public class Hand {

private ArrayList<Character> letters;



public Hand() {
    letters = new ArrayList<Character>();
}

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void addLetter(Character letter) {
    letters.add(letter);

}

public void setLetters(ArrayList<Character> bagChars){
    this.letters = bagChars;
}

public void removeLetter(Character letter) {
    letters.remove(letter);
}


public void printHand() {
    System.out.print("Letters in hand: ");
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

}
