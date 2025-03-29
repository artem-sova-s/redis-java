package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisServer {
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public void start(final int port, final int threadPoolSize) throws IOException {
        log.info("Starting Redis Server on port " + port);

        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);

        // creation of the thread pool
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);

        while(true) {
            Socket socket = this.serverSocket.accept();
            log.debug("New connection from " + socket.getRemoteSocketAddress());

            this.threadPool.submit(new RedisClientHandler(socket));
        }
    }

    public void stop() {
        this.threadPool.shutdown();

        try {
            if (!this.threadPool.awaitTermination(20, TimeUnit.SECONDS)) {
                this.threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        if (this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                log.error("Failed to close socket connection: ", e.getMessage());
                System.exit(1);
            }
        }
    }
}
