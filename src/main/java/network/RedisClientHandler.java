package network;

import command.handler.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// command processing components
import command.CommandParser;
import command.CommandContext;

// pipeline command handler factory
import command.handler.CommandHandlerFactory;

// command execution result enum
import utils.network.CommandStatus;

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
            CommandHandler commandHandler = CommandHandlerFactory.createPipeline();
            log.debug("Got command handler");

            CommandParser commandParser = new CommandParser();
            CommandContext commandContext = null;

            String clientMessage = null;

            while ((clientMessage = inputReader.readLine()) != null) {
                log.debug("Parsing command");

                // parse the command
                commandContext = commandParser.parse(clientMessage, outputStream);

                log.debug("Handling command");
                CommandStatus status = commandHandler.handle(commandContext);
                log.debug("Command status: " + status.toString());

                // is the command execution return code is EXIT then close connection and exit the thread
                if (status == CommandStatus.EXIT) {
                    log.info("Exiting the thread...");
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Failed to handle client", e.getMessage());
        } finally {
            // Defensive close operations
            exitThread();
        }
    }

    private void exitThread() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            Thread.currentThread().interrupt();
            return;
        } catch (IOException e) {
            log.error("Error while closing resources" + e.getMessage());
        }
    }
}
