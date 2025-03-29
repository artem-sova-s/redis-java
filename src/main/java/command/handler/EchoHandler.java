package command.handler;
import command.CommandContext;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.network.CommandStatus;


public class EchoHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public EchoHandler(final CommandHandler next) {
        super(next);
    }

    public EchoHandler() {
        super();
    }

    @Override
    protected boolean canHandle(String command) {
        return command.equalsIgnoreCase("echo");
    }

    @Override
    protected CommandStatus process(CommandContext context) throws IOException {
        log.debug("Can handle command: " + context.getCommand() + context.getCommandArgs().toString());

        try {
            String reponseMessage = String.join(" ", context.getCommandArgs()) + "\r\n";
            context.getOutputStream().write(reponseMessage.getBytes());
        } catch (IOException e) {
            log.error("IOException while processing command" + e.getMessage());
        } catch (NullPointerException e) {
            log.error("NullPointerException while processing command" + e.getMessage());
        }
        return CommandStatus.CONTINUE;
    }
}
