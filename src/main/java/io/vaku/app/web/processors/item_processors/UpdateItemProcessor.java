package io.vaku.app.web.processors.item_processors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.vaku.app.domain.Item;
import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.BadRequestException;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.web.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UpdateItemProcessor implements RequestProcessor  {
    private final ItemDAO itemDAO;

    public UpdateItemProcessor(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item;

        try {
            item = gson.fromJson(request.getBody(), Item.class);
        } catch (JsonSyntaxException e) {
            throw new BadRequestException("Can't parse json body");
        }

        if (item.getId() == null) {
            throw new BadRequestException("Invalid request body: 'id' can't be null");
        }

        if (item.getTitle() == null) {
            throw new BadRequestException("Invalid request body: 'title' can't be null");
        }

        if (item.getPrice() == null) {
            throw new BadRequestException("Invalid request body: 'price' can't be null");
        }

        String itemJson = gson.toJson(itemDAO.update(item));
        String response =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                itemJson;
        output.write(response.getBytes(StandardCharsets.UTF_8));
        output.close();
    }
}
