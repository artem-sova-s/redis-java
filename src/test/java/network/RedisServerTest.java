package network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// for RedisClient
import java.io.*;
import java.net.Socket;

class RedisClient {
    private Socket socket = null;

    public RedisClient(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.err.println("Failed to create client socket: " + e.getMessage());
        }
    }

    public String sendMessage(String message) {
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Failed to write to output stream: " + e.getMessage());
        }

        String serverResponse = null;
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            serverResponse = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Failed to read from input stream: " + e.getMessage());
        }

        return serverResponse;
    }
}

public class RedisServerTest {
    private static final int port = 5555;
    private RedisServer redisServer = null;


    @BeforeEach
    void setUp() {
        redisServer = new RedisServer();
    }

    @AfterEach
    void tearDown() {
        try {
            redisServer.stop();
        } catch (Exception e) {
            System.err.println("Failed to close server: " + e.getMessage());
        }
    }

    @DisplayName("Validates instance creation")
    @Test
    void RedisServer_returnsRedisServerInstance() {
        assertNotNull(redisServer);
    }

//    @DisplayName("Returns PONG")
//    @Test
//    void RedisServer_returnSameMessage() {
//        Thread serverThread = new Thread(() -> {
//            try {
//                redisServer.start(port);
//            } catch (IOException e) {
//                e.getMessage();
//            }
//        });
//
//        System.out.println("Starting server thread");
//        // start redis server in the separate thread
//        serverThread.start();
//
//        // wait till server starts
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.getMessage();
//        }
//
//        try {
//            RedisClient redisClient = new RedisClient("localhost", port);
//            String response = redisClient.sendMessage("echo PONG");
//
//            System.out.println("Response: " + response);
//
//            assertNotNull(response);
//            assertEquals("PONG\r\n", response);
//        } catch (Exception e) {
//            System.err.println("Failed to start server: " + e.getMessage());
//        }
//    }
}
