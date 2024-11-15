import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {

    private Set<String> englishWords;
    private Scanner scanner;
    private CommandWords words;

    public Parser() {
        englishWords = new HashSet<>();
        scanner = new Scanner(System.in);
        words = new CommandWords();
    }

    /**
     *This method loads the dictionary file
     */
    public void loadWordsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                englishWords.add(word.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading the word list: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        return englishWords.contains(word.toUpperCase());
    }

    /**
     *This method gets the current command inputted by the player
     */
    public Command getCurrentCommand() {
        String input;

        input = scanner.nextLine();
        Scanner commandScanner = new Scanner(input);
        if (commandScanner.hasNext()) {
            input = commandScanner.next();
        }

        if (words.isValidCommand(input)) {
            return new Command(input);
        }
        else {
            return new Command(null);
        }
    }

}


