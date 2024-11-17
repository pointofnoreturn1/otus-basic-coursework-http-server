package io.vaku.app.web.processors;

import io.vaku.app.web.HttpRequest;
import io.vaku.app.web.exception.NotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class StaticFileProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String fileName = request.getUri().substring(1);

        try (Stream<Path> paths = Files.walk(Paths.get("src/main/resources/static"))) {
            Optional<Path> file = paths
                    .filter(Files::isRegularFile)
                    .filter(it -> it.getFileName().toString().equals(fileName))
                    .findFirst();

            if (file.isPresent()) {
                String response =
                        "HTTP/1.1 200 Ok\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "\r\n" +
                        Files.readString(file.get(), StandardCharsets.UTF_8);
                output.write(response.getBytes(StandardCharsets.UTF_8));
                output.close();
            } else {
                throw new NotFoundException("Page Not Found");
            }
        }
    }
}
