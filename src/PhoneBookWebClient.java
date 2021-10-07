import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class PhoneBookWebClient {
    public static void main(String[] args) {
        String list = "";
        var scan = new Scanner(System.in);
        var mapper = new ObjectMapper();
        try {
            System.out.println("Enter FirstName");
            var firstname = scan.nextLine();
            System.out.println("Enter LastName");
            var lastname = scan.nextLine();
            System.out.println("Enter Age");
            var age = scan.nextLine();
            System.out.println("Enter Phone");
            var phone = scan.nextLine();
            System.out.println("Enter Adress");
            var adress = scan.nextLine();
           list = mapper.writeValueAsString(List.of("generate", firstname, lastname, age, phone, adress));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/phonebook"))
                .POST(HttpRequest.BodyPublishers.ofString(list))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
