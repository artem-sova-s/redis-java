package command.handler;

import command.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.network.CommandStatus;

import java.io.IOException;

// goes at the end of the pipeline and called when no more other command is called in the pipeline
public class InvalidCommandHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(InvalidCommandHandler.class);

    public InvalidCommandHandler() { super(); }

    @Override
    public boolean canHandle(final String command) {
        return true;
    }

    @Override
    protected CommandStatus process(final CommandContext context) throws IOException {
        try {
            context.getOutputStream().write("UNKNOWN COMMAND\r\n".getBytes());
        } catch (IOException e) {
            log.error("Failed to write to output stream in InvalidCommandHandler: " + e.getMessage());
        }
        return CommandStatus.CONTINUE;
    }
}
