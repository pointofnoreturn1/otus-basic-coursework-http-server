package io.vaku.app.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String rawRequest;
    private HttpMethod method;
    private String uri;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private String pathVariable;
    private String body;
    private Exception exception;

    private static final Logger logger = LogManager.getLogger(HttpRequest.class);

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        this.parse();
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getUri() {
        return uri;
    }

    public String getRoutingKey() {
        return method + " " + uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getPathVariable() {
        return pathVariable;
    }

    public String getBody() {
        return body;
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ') + 1;
        int endIndex = rawRequest.indexOf(' ', startIndex);
        this.uri = rawRequest.substring(startIndex, endIndex);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex - 1));
        parseHeaders();
        parseParameters();
        parsePathVariable();
        parseBody();
    }

    private void parseHeaders() {
        String stringHeaders = rawRequest.substring(rawRequest.indexOf("\r\n") + 2, rawRequest.indexOf("\r\n\r\n"));
        for (String line : stringHeaders.split("\r\n")) {
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }
    }

    private void parseParameters() {
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private void parsePathVariable() {
        String[] tokens = uri.split("/");
        if (tokens.length >= 3) {
            this.pathVariable = tokens[2];
        }
    }

    private void parseBody() {
        if (method != HttpMethod.GET) {
            this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
    }

    public void info(boolean debug) {
        if (debug) {
            logger.info(rawRequest);
        }
        logger.info("Method: {}", method);
        logger.info("URI: {}", uri);
        logger.info("Headers: {}", headers);
        logger.info("Parameters: {}", parameters);
        logger.info("Body: {}", body);
    }
}
