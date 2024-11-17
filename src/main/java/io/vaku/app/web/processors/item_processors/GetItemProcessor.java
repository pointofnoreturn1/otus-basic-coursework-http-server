package io.vaku.app.web.processors.item_processors;

import com.google.gson.Gson;
import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.domain.Item;
import io.vaku.app.web.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetItemProcessor implements RequestProcessor {
    private final ItemDAO itemDAO;

    public GetItemProcessor(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        List<Item> items = new ArrayList<>();

        if (request.getPathVariable() != null) {
            Optional<Item> item = itemDAO.getById(Long.parseLong(request.getPathVariable()));
            item.ifPresent(items::add);
        } else {
            items.addAll(itemDAO.getAll());
        }

        Gson gson = new Gson();
        String itemsJson = gson.toJson(items);

        String response =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                itemsJson;
        output.write(response.getBytes(StandardCharsets.UTF_8));
        output.close();
    }
}
