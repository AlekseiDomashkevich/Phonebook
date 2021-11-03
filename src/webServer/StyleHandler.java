package webServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StyleHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var file = new File("./public/style.css");
        var response = Files.readAllBytes(Paths.get(file.getPath()));
        exchange.sendResponseHeaders(200, response.length);
        var out = exchange.getResponseBody();
        out.write(response);
        out.flush();
        out.close();
    }
}
