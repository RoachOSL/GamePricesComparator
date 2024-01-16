package dev.Roach;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class DealsFetcher {

    final HttpClient client;

    public DealsFetcher(HttpClient client) {
        this.client = client;
    }

    public DealsFetcher() {
        this.client = HttpClient.newBuilder().build();
    }

    //My solution, around 1min 30s
    //I added writing to the file to omit api calls limit.
    public List<String> getAllDeals() {

        List<String> allPages = new ArrayList<>();

        try (FileWriter fw = new FileWriter("listOfAllDeals.txt")){
            int pageNumber = 0;
            int totalPages = Integer.MAX_VALUE;

            while (pageNumber < totalPages) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?pageNumber" + pageNumber))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String pageCountHeader = response.headers().firstValue("X-Total-Page-Count").orElse("1");
                totalPages = Integer.parseInt(pageCountHeader);

                String responseBody = response.body();
                allPages.add(responseBody);

                fw.write(responseBody);

                pageNumber++;
            }
            return allPages;

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Chat-gpt solution it takes around 35s.
    //It is also missing writer, but I can add it easily.

//    public List<String> getAllDeals() {
//        List<CompletableFuture<String>> futures = new ArrayList<>();
//
//        HttpRequest initialRequest = HttpRequest.newBuilder()
//                .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?pageNumber=0"))
//                .GET()
//                .build();
//
//        try {
//            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
//            int totalPages = Integer.parseInt(initialResponse.headers().firstValue("X-Total-Page-Count").orElse("1"));
//
//            for (int pageNumber = 0; pageNumber < totalPages; pageNumber++) {
//                HttpRequest pageRequest = HttpRequest.newBuilder()
//                        .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?pageNumber=" + pageNumber))
//                        .GET()
//                        .build();
//
//                CompletableFuture<String> future = client.sendAsync(pageRequest, HttpResponse.BodyHandlers.ofString())
//                        .thenApply(HttpResponse::body);
//                futures.add(future);
//            }
//
//            return futures.stream()
//                    .map(CompletableFuture::join)
//                    .collect(Collectors.toList());
//
//        } catch (InterruptedException | IOException exception) {
//            exception.printStackTrace();
//            return new ArrayList<>();
//        }
//    }

    public String getDealUsingID(String id) {
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
