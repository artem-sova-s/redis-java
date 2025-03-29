package command.handler;

import command.CommandContext;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public PingHandler(CommandHandler next) {
        super(next);
    }

    @Override
    protected boolean canHandle(String command) {
        return command.equalsIgnoreCase("ping");
    }

    @Override
    protected void process(CommandContext context) throws IOException {
        try {
            log.info("Pinging command: " + context.getCommand());
            context.getOutputStream().write("PONG\r\n".getBytes());
        } catch (IOException e) {
            log.error("Failed to write PING response: " + e.getMessage());
        }
    }
}
