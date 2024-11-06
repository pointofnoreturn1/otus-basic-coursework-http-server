package io.vaku.app;

import io.vaku.app.web.HttpServer;

public class Application {
    public static void main(String[] args) {
        new HttpServer(8189).start();
    }
}
