package network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// for RedisClient
import java.io.OutputStream;
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

    public void sendMessage(String message) {
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write(message.getBytes());
        } catch (Exception e) {
            System.err.println("Failed to get output stream: " + e.getMessage());
        }
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

    @DisplayName("Returns PONG")
    @Test
    void RedisServer_returnsPong() {
        try {
            redisServer.start(port);

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}
