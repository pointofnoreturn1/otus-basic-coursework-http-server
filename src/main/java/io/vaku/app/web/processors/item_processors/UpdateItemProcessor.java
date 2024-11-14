package io.vaku.app.web.processors.item_processors;

import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.HttpRequest;
import io.vaku.app.web.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateItemProcessor implements RequestProcessor  {
    private final ItemDAO itemDAO;

    public UpdateItemProcessor(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {

    }
}
