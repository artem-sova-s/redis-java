package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import command.handler.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// command processing components
import command.CommandParser;
import command.CommandContext;

import command.handler.NullableHandler;
import command.handler.EchoHandler;
import command.handler.InvalidCommandHandler;

public class RedisServer {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public void start(int port) throws IOException {

        log.info("Created server socket on " + port + " port");

        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        this.clientSocket = serverSocket.accept();

        // init of the input stream
        this.inputStream = clientSocket.getInputStream();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        log.debug("Got input stream");

        outputStream = clientSocket.getOutputStream();
        log.debug("Got output stream");

        // command handler pipeline creation
        CommandHandler commandHandler = new NullableHandler(new EchoHandler(new InvalidCommandHandler()));
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
    }

    public void stop() throws IOException {
        // Defensive close operations
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            log.error("Error while closing resources" + e);
        }
    }
}
