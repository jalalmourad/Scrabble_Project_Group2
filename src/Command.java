/**
 * Class to verify user command inputs.
 */

public class Command {
    private String command;

    public Command(String c) {
        command = c;
    }

    public String getCommand() {
        return command;
    }

    public boolean isCommand() {
        return (command != null);
    }
}
