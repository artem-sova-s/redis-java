package command.handler;

import command.CommandContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.network.CommandStatus;

import java.io.IOException;

public class NullableHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(NullableHandler.class);

    public NullableHandler(CommandHandler next) {
        super(next);
    }

    public NullableHandler() {
        super();
    }

    @Override
    protected boolean canHandle(String command) {
        // if command is equal to null then the command must be treated as empty
        log.debug("Nullable handler: " + command);
        return command == null;
    }

    @Override
    protected CommandStatus process(CommandContext context) {
        return CommandStatus.CONTINUE;
    }
}
