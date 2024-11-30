import java.io.Serializable;

/**
 * Class to maintain known and accepted commands in the Scrabble game.
 */
public class CommandWords implements Serializable {

    private static final String[] commandWords = {"play", "pass", "quit"};

    /**
     * Check if valid command.
     *
     * @param command
     * @return
     */
    public boolean isValidCommand(String command) {
        for (int i = 0; i < commandWords.length; i++) {
            if (command.equals(commandWords[i])) {
                return true;
            }
        }
        return false;
    }
}
