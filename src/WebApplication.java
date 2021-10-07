import WebServer.PhoneBookHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebApplication {
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/phonebook", new PhoneBookHandler());
        server.setExecutor(null); // creates a default executor

        System.out.println("Starting HTTP service on :8080...");
        server.start();
    }
}
