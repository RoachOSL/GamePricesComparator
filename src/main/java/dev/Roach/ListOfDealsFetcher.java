package dev.Roach;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ListOfDealsFetcher {

    final HttpClient client;

    public ListOfDealsFetcher(HttpClient client) {
        this.client = client;
    }

    public ListOfDealsFetcher() {
        this.client = HttpClient.newBuilder().build();
    }

    public String getAllDeals() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/deals"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public String getSpecificDealUsingDealsID(String id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?id=" + id))
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
