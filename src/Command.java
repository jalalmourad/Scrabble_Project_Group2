/**
 * Class to verify user command inputs.
 */

public class Command {
    private String command;

    public Command(String c) {
        command = c;
    }


    public boolean isCommand() {
        return (command != null);
    }
}
