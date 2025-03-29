package command.handler;

import command.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.network.CommandStatus;

import java.io.IOException;


public class ExitHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(ExitHandler.class);

    public ExitHandler(CommandHandler commandHandler) {
        super(commandHandler);
    }

    @Override
    protected boolean canHandle(String command) {
        return command.equalsIgnoreCase("exit");
    }

    @Override
    protected CommandStatus process(CommandContext context) throws IOException {
        log.info("Exit command");
        context.getOutputStream().write("EXIT\r\n".getBytes());
        return CommandStatus.EXIT;
    }
}