package dev.Roach;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DealsFetcher {

    private final HttpClient client;

    public DealsFetcher(HttpClient client) {
        this.client = client;
    }

    public List<String> getAllDeals() {

        List<String> allPages = new ArrayList<>();

        HttpRequest initialRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?pageNumber=0"))
                .GET()
                .build();

        try (FileWriter fw = new FileWriter("listOfAllDeals.txt")) {

            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
            int totalPages = Integer.parseInt(initialResponse.headers().firstValue("X-Total-Page-Count").orElse("0"));

            for (int i = 0; i < totalPages; i++) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?maxAge=240&pageNumber=" + i))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                allPages.add(response.body());
                fw.write(response.body());

            }
            return allPages;

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String getDealUsingID(String id) {

        if (id == null) {
            return "NULL";
        }

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