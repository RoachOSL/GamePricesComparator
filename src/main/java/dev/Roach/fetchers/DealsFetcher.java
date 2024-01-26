package dev.Roach.fetchers;

import java.io.BufferedReader;
import java.io.FileReader;
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

        try (FileWriter fw = new FileWriter("dataFromApi/AllDealsList.txt")) {

            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
            int totalPages = Integer.parseInt(initialResponse.headers().firstValue("X-Total-Page-Count").get());

            int maxAgeDealUptimeInHours = 240;

            fw.write("[");

            for (int i = 0; i < totalPages; i++) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(String.format("https://www.cheapshark.com/api/1.0/deals?maxAge=%d&pageNumber=%d"
                                , maxAgeDealUptimeInHours, i)))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                allPages.add(response.body());
                fw.write(response.body());

                if (i < totalPages - 1) {
                    fw.write(",");
                }

            }

            fw.write("]");

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

        try (FileWriter fw = new FileWriter("dataFromApi/DealByIDList.txt")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            fw.write(response.body());

            return response.body();

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public String readAllDealsFromFile() {

        StringBuilder allDeals = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("dataFromApi/AllDealsList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                allDeals.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allDeals.toString();
    }

    public String readDealUsingIDFromFile() {

        StringBuilder dealByID = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("dataFromApi/DealByIDList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dealByID.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dealByID.toString();
    }


}
