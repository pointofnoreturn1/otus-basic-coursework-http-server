package io.vaku.app.web;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket serverSocket;
    private final Dispatcher dispatcher;

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
            e.printStackTrace();
        }
    }
}
