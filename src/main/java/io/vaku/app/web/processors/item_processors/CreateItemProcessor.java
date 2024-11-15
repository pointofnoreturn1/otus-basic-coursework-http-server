package io.vaku.app.web.processors.item_processors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.vaku.app.domain.ItemDAO;
import io.vaku.app.domain.ItemPostRequest;
import io.vaku.app.web.exception.BadRequestException;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.web.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateItemProcessor implements RequestProcessor {
    private final ItemDAO itemDAO;

    public CreateItemProcessor(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        ItemPostRequest item;

        try {
            item = new Gson().fromJson(request.getBody(), ItemPostRequest.class);
        } catch (JsonSyntaxException e) {
            throw new BadRequestException("Can't parse json body");
        }

        if (item.title() == null) {
            throw new BadRequestException("Invalid request body: 'title' can't be null");
        }

        if (item.price() == null) {
            throw new BadRequestException("Invalid request body: 'price' can't be null");
        }

        itemDAO.create(item);
        String response =
                "HTTP/1.1 201 Created\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        output.close();
    }
}
