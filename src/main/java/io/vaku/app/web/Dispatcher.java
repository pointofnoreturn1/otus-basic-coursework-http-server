package io.vaku.app.web;

import io.vaku.app.domain.ItemDAO;
import io.vaku.app.web.exception.BadRequestException;
import io.vaku.app.web.exception.MethodNotAllowedException;
import io.vaku.app.web.exception.NotFoundException;
import io.vaku.app.web.processors.*;
import io.vaku.app.web.processors.default_processors.DefaultBadRequestProcessor;
import io.vaku.app.web.processors.default_processors.DefaultInternalServerErrorProcessor;
import io.vaku.app.web.processors.default_processors.DefaultMethodNotAllowedProcessor;
import io.vaku.app.web.processors.default_processors.DefaultNotFoundProcessor;
import io.vaku.app.web.processors.item_processors.CreateItemProcessor;
import io.vaku.app.web.processors.item_processors.DeleteItemProcessor;
import io.vaku.app.web.processors.item_processors.GetItemProcessor;
import io.vaku.app.web.processors.item_processors.UpdateItemProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final Map<String, RequestProcessor> processors;
    private final RequestProcessor defaultNotFoundProcessor;
    private final RequestProcessor defaultInternalServerErrorProcessor;
    private final RequestProcessor defaultBadRequestProcessor;
    private final RequestProcessor defaultMethodNotAllowedProcessor;
    private final RequestProcessor staticFileProcessor;

    public Dispatcher() {
        ItemDAO itemDAO = new ItemDAO();
        this.defaultNotFoundProcessor = new DefaultNotFoundProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorProcessor();
        this.defaultBadRequestProcessor = new DefaultBadRequestProcessor();
        this.defaultMethodNotAllowedProcessor = new DefaultMethodNotAllowedProcessor();
        this.staticFileProcessor = new StaticFileProcessor();
        this.processors = new HashMap<>();
        this.processors.put("GET /items", new GetItemProcessor(itemDAO));
        this.processors.put("POST /items", new CreateItemProcessor(itemDAO));
        this.processors.put("PUT /items", new UpdateItemProcessor(itemDAO));
        this.processors.put("DELETE /items", new DeleteItemProcessor(itemDAO));
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            for (Map.Entry<String, RequestProcessor> processor : processors.entrySet()) {
                if (request.getRoutingKey().startsWith(processor.getKey())) {
                    processor.getValue().execute(request, out);
                    return;
                }
            }

            if (!processors.containsKey(request.getRoutingKey())) {
                for (String key : processors.keySet()) {
                    if (key.split(" ")[1].startsWith(request.getUri())) {
                        throw new MethodNotAllowedException("Method Not Allowed: " + request.getMethod());
                    }
                }
            }

            if (request.getMethod().equals(HttpMethod.GET)) {
                staticFileProcessor.execute(request, out);
            } else {
                throw new NotFoundException("Page Not Found");
            }
        } catch (NotFoundException e) {
            request.setException(e);
            defaultNotFoundProcessor.execute(request, out);
        } catch (MethodNotAllowedException e) {
            request.setException(e);
            defaultMethodNotAllowedProcessor.execute(request, out);
        } catch (BadRequestException e) {
            request.setException(e);
            defaultBadRequestProcessor.execute(request, out);
        } catch (Exception e) {
            request.setException(e);
            defaultInternalServerErrorProcessor.execute(request, out);
        }
    }
}
