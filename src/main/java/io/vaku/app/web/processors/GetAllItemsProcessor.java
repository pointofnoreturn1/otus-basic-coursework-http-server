package io.vaku.app.web.processors;

import com.google.gson.Gson;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.domain.Item;
import io.vaku.app.domain.ItemsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllItemsProcessor implements RequestProcessor {
    private ItemsRepository itemsRepository;

    public GetAllItemsProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        List<Item> items = itemsRepository.getItems();
        Gson gson = new Gson();
        String itemsJson = gson.toJson(items);

        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                itemsJson;
        output.write(response.getBytes(StandardCharsets.UTF_8));
        output.close();
    }
}
