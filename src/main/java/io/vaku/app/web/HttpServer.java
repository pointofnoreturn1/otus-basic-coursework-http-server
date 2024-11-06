package io.vaku.app.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger logger = LogManager.getLogger(HttpServer.class);
    private final int port;
    private final Dispatcher dispatcher;
    private final ExecutorService executorService;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("HTTP server started on port {}", port);
            while (true) {
                executorService.execute(new ClientHandler(serverSocket.accept(), dispatcher));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
