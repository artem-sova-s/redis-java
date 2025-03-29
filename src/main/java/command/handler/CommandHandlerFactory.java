package command.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandHandlerFactory {
    private static final Logger log = LoggerFactory.getLogger(CommandHandlerFactory.class);

    public static CommandHandler createPipeline() {
        InvalidCommandHandler invalidCommandHandler = new InvalidCommandHandler();
        ExitHandler exitHandler = new ExitHandler(invalidCommandHandler);
        EchoHandler echoHandler = new EchoHandler(exitHandler);
        PingHandler pingHandler = new PingHandler(echoHandler);

        return new NullableHandler(pingHandler);
    }
}
