import network.RedisServer;
import config.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Config config = null;

        try {
            config = Config.getConfig();
        } catch (IllegalArgumentException e) {
            log.error("Error while parsing config: ", e);
            System.exit(1);
        }

        RedisServer redisServer = null;
        try {
            redisServer = new RedisServer();
            redisServer.start(config.getInt("server.port"));

        } catch (IOException e) {
            System.out.println("Failed to start redisServer: " + e.getMessage());
        } finally {
            try {
                redisServer.stop();
            } catch (IOException e) {
                System.out.println("Failed to stop redisServer: " + e.getMessage());
            }
        }
    }
}
