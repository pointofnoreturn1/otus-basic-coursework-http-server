package io.vaku.app.web.processors.item_processors;

import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.BadRequestException;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.web.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DeleteItemProcessor implements RequestProcessor  {
    private final ItemDAO itemDAO;

    public DeleteItemProcessor(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (request.getPathVariable() != null) {
            itemDAO.delete(Long.parseLong(request.getPathVariable()));
            String response =
                    "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            output.close();
        } else {
            throw new BadRequestException("The 'id' path variable is required");
        }
    }
}
