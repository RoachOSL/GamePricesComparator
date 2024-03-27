package dev.Roach.fetchers;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Setter
@Getter
public class DealsFetcher {
    private HttpClient client = HttpClient.newBuilder().build();
    private static final String DEALS_API_URL = "https://www.cheapshark.com/api/1.0/deals?";

    public String getDealUsingID(String id) {

        if (id == null) {
            return "";
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DEALS_API_URL + "id=" + id))
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