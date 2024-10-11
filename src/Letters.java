import java.util.*;

public class Letters {
    public Map<Character, Integer> letterWorth;
    public List<Character> letterBag;
    Random rand  = new Random();

    public Letters() {
        letterWorth = new HashMap<>();
        letterBag = new ArrayList<>();
        setLetterWorth();
        initBag();
    }

    public void setLetterWorth() {
        for (char i = 'A'; i <= 'Z'; i++) {
            if (i == 'A' || i == 'E' || i == 'I' || i == 'O' || i == 'U'
                    || i == 'L' || i == 'N' || i == 'S' || i == 'T' || i == 'R') {
                letterWorth.put(i, 1);
            }
            else if (i == 'D' || i == 'G') {
                letterWorth.put(i, 2);
            }
            else if (i == 'B' || i == 'C' || i == 'M' || i == 'P') {
                letterWorth.put(i, 3);
            }
            else if (i == 'F' || i == 'H' || i == 'V' || i == 'W' || i == 'Y') {
                letterWorth.put(i, 4);
            }
            else if (i == 'K') {
                letterWorth.put(i, 5);
            }
            else if (i == 'J' || i == 'X') {
                letterWorth.put(i, 8);
            }
            else { // Q, Z
                letterWorth.put(i, 10);
            }
        }
    }

    /**
     * Initialize bag of letters containing a selection of 100 random letters.
     */
    public void initBag() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 100; i++) {
            letterBag.add(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
    }

    /**
     * Testing purposes
     */
    public static void main(String[] args) {
        Letters l = new Letters();
        System.out.println(l.letterWorth);
        System.out.println(l.letterBag);
    }
}
