package WebServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.IController;
import controller.PhonebookController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class PhoneBookHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        var mapper = new ObjectMapper();
        var phonebookController = new PhonebookController();
        var list = t.getRequestBody();
        List<String> arguments = mapper.readValue(list, new TypeReference<List<String>>(){});
//        var list = new LinkedList<String>();
//        list.add("generate");
//        list.add("vasia");
//        list.add("pupkin");
//        list.add("20");
//        list.add("2020");
//        list.add("Brest");
        var responseEntity = phonebookController.process(arguments);
        var output = new ByteArrayOutputStream();
        mapper.writeValue(output, responseEntity);

        System.out.println("Handling: " + t.getRequestURI());
        t.sendResponseHeaders(200, output.size());
        OutputStream os = t.getResponseBody();
        os.write(output.toByteArray());
        os.close();
    }
}
