package dev.Roach.fetcher;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StoresFetcher {
    private final HttpClient client;

    public StoresFetcher(HttpClient client) {
        this.client = client;
    }

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
