package command.handler;

import network.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public class CommandHandlerFactory {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    public static CommandHandler createPipeline() {
        InvalidCommandHandler invalidCommandHandler = new InvalidCommandHandler();
        ExitHandler exitHandler = new ExitHandler(invalidCommandHandler);
        EchoHandler echoHandler = new EchoHandler(exitHandler);
        PingHandler pingHandler = new PingHandler(echoHandler);

        return new NullableHandler(pingHandler);
    }
}
