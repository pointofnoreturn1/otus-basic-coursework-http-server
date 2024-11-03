package io.vaku.app.processors;

import io.vaku.app.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultMethodNotAllowedProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String response = "HTTP/1.1 405 Method Not Allowed\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
