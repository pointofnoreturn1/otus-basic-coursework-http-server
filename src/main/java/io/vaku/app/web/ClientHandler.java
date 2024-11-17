package io.vaku.app.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket serverSocket;
    private final Dispatcher dispatcher;

    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(Socket serverSocket, Dispatcher dispatcher) {
        this.serverSocket = serverSocket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[8192];
            int n = serverSocket.getInputStream().read(buffer);
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, serverSocket.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }
}
