import java.io.Serializable;

/**
 * Class to verify user command inputs.
 */

public class Command implements Serializable {
    private String command;

    public Command(String c) {
        command = c;
    }


    public boolean isCommand() {
        return (command != null);
    }
}
