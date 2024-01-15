package dev.Roach;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ListOfStoresFetcher {
    final HttpClient client;

    public ListOfStoresFetcher(HttpClient client) {
        this.client = client;
    }

    public ListOfStoresFetcher() {
        this.client = HttpClient.newBuilder().build();
    }

    //It is possible to do a GET request and retrieve information of a store using its ID;

    public String getGameContainingSpecificKeyword(String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?title=" + encodedKeyword))
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
