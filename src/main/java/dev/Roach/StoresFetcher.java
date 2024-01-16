package dev.Roach;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StoresFetcher {
    final HttpClient client;

    public StoresFetcher(HttpClient client) {
        this.client = client;
    }

    public StoresFetcher() {
        this.client = HttpClient.newBuilder().build();
    }

    //I cannot retrieve single shop by ID at this point, I will try to save this list to file,
    //than create java object and make method to retrieve shop by ID.

    public String getAllShops() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/stores"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }

}
