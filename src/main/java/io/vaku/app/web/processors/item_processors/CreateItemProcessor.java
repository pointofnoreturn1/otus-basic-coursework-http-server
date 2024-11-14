package io.vaku.app.web.processors.item_processors;

import com.google.gson.Gson;
import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.domain.Item;
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
//        Gson gson = new Gson();
//        Item item = itemDAO.createOrUpdate(gson.fromJson(request.getBody(), Item.class));
//
//        String response = "" +
//                "HTTP/1.1 201 Created\r\n" +
//                "Content-Type: application/json\r\n" +
//                "\r\n" +
//                gson.toJson(item);
//        output.write(response.getBytes(StandardCharsets.UTF_8));
//        output.close();
    }
}
