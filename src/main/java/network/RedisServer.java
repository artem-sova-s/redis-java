package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisServer {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private BufferedReader inputStream;

    public void start(int port) throws IOException {

        log.info("Created server socket on " + port + " port");

        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        this.serverSocket.setReuseAddress(true);
        this.clientSocket = serverSocket.accept();

        this.inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outputStream = clientSocket.getOutputStream();
        String clientMessage = null;

        while ((clientMessage = inputStream.readLine()) != null) {
            System.out.println(clientMessage);

            if (clientMessage.equalsIgnoreCase(".")) {
                break;
            }

            if (clientMessage.equalsIgnoreCase("ping")) {
                outputStream.write("PONG\r\n".getBytes());
            }
        }
    }

    public void stop() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.serverSocket.close();
        this.clientSocket.close();
    }

}
