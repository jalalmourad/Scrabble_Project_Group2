import java.util.Scanner;

public class Parser {
    private CommandWords words;
    private Scanner scanner;

    /**
     * Create parser for processing commands from CLI.
     */
    public Parser() {
        scanner = new Scanner(System.in);
        words = new CommandWords();
    }


    public Command getCurrentCommand() {
        String input;

        System.out.print("> ");

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
