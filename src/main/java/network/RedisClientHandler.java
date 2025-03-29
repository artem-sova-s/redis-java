package network;

import command.handler.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// command processing components
import command.CommandParser;
import command.CommandContext;

import command.handler.NullableHandler;
import command.handler.EchoHandler;
import command.handler.InvalidCommandHandler;
import command.handler.PingHandler;

import java.io.*;
import java.net.Socket;


public class RedisClientHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    private Socket clientSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public RedisClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // init of the input stream
            this.inputStream = clientSocket.getInputStream();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            log.debug("Got input stream");

            outputStream = clientSocket.getOutputStream();
            log.debug("Got output stream");

            // command handler pipeline creation
            CommandHandler commandHandler = new NullableHandler(new PingHandler(new EchoHandler(new InvalidCommandHandler())));
            log.debug("Got command handler");

            CommandParser commandParser = new CommandParser();
            CommandContext commandContext = null;

            String clientMessage = null;

            while ((clientMessage = inputReader.readLine()) != null) {
                log.debug("Parsing command");

                // parse the command
                commandContext = commandParser.parse(clientMessage, outputStream);

                log.debug("Handling command");
                commandHandler.handle(commandContext);
            }
        } catch (IOException e) {
            log.error("Failed to handle client", e.getMessage());
        } finally {
            // Defensive close operations
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (IOException e) {
                log.error("Error while closing resources" + e);
            }
        }
    }
}
