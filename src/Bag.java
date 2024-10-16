import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {

    private static final List<Character> alphabet = new ArrayList<>();

    private ArrayList<Character> bagChars;

    static {
        addChars('A', 9);
        addChars('B', 3);
        addChars('C', 3);
        addChars('D', 4);
        addChars('E', 12);
        addChars('F', 2);
        addChars('G', 3);
        addChars('H', 2);
        addChars('I', 9);
        addChars('J', 1);
        addChars('K', 1);
        addChars('L', 4);
        addChars('M', 2);
        addChars('N', 6);
        addChars('O', 8);
        addChars('P', 2);
        addChars('Q', 1);
        addChars('R', 6);
        addChars('S', 4);
        addChars('T', 6);
        addChars('U', 4);
        addChars('V', 2);
        addChars('W', 2);
        addChars('X', 1);
        addChars('Y', 2);
        addChars('Z', 1);
    }

    public Bag(){
    bagChars = new ArrayList<>();
    }

    public ArrayList<Character> getAlphabet() {
        Random rand = new Random();

        for (int i = 0;i<7;i++){
            bagChars.add(alphabet.get(rand.nextInt(alphabet.size())));
            alphabet.remove(rand.nextInt(alphabet.size()));
        }
        return bagChars;
    }

    public char addRandomChar(){
        Random rand = new Random();

        char c = alphabet.get(rand.nextInt(alphabet.size()));
            alphabet.remove(rand.nextInt(alphabet.size()));
        return c;
    }

    public static void addChars(char c, int n){
        for(int i = 0; i < n; i++){
            alphabet.add(c);
        }
    }


}
