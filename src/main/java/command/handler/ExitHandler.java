package command.handler;

import command.CommandContext;
import network.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExitHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    public ExitHandler(CommandHandler commandHandler) {
        super(commandHandler);
    }

    @Override
    protected boolean canHandle(String command) {
        return command.equalsIgnoreCase("exit");
    }

    @Override
    protected void process(CommandContext context) throws IOException {
        // TODO: implement connection interrupt and thread exit
        return;
    }
}
