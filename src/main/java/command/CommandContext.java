package command;

import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandContext {
    private static final Logger log = LoggerFactory.getLogger(CommandContext.class);

    private OutputStream outputStream = null;
    private String command;

    // list of arguments for the command execution
    private List<String> commandArgs = null;

    public CommandContext(String command, OutputStream out, List<String> args) {
        log.info("New CommandContext for command: " + command);

        this.command = command;
        this.outputStream = out;
        this.commandArgs = args;
    }

    /*
     * This overrided constructor is used for NULL object
     * for the case when command is invalid or empty
     */
    public CommandContext(final OutputStream out) {
        log.info("Nullable CommandContext created");
        this.command = null;
        this.outputStream = out;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getCommandArgs() {
        return commandArgs;
    }
}
