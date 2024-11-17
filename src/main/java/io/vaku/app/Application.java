package io.vaku.app;

import io.vaku.app.web.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static final int DEFAULT_PORT = 8189;
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            new HttpServer(DEFAULT_PORT).start();
            return;
        } else if (args.length == 2 && args[0].equals("-p")) {
            try {
                new HttpServer(Integer.parseInt(args[1])).start();
            } catch (NumberFormatException e) {
                logger.error("Failed to parse port: {}", args[1]);
            }
        }

        logger.error("Specify port with '-p' argument. Default port is {}", DEFAULT_PORT);
    }
}
