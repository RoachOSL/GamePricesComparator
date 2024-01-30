package dev.Roach.fetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.JSONMapper;
import dev.Roach.datamodel.deal.DealAllListPojo;
import dev.Roach.datamodel.deal.DealAllPojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class DealsFetcher {

    private final HttpClient client;

    public DealsFetcher(HttpClient client) {
        this.client = client;
    }

    public ArrayList<DealAllListPojo> getAllDeals() {

        ArrayList<DealAllListPojo> allPages = new ArrayList<>();
        JSONMapper jsonMapper = new JSONMapper();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpRequest initialRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.cheapshark.com/api/1.0/deals?pageNumber=0"))
                .GET()
                .build();

        try {

            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
            int totalPages = Integer.parseInt(initialResponse.headers().firstValue("X-Total-Page-Count").get());

            int maxAgeDealUptimeInHours = 240;

            for (int i = 0; i < totalPages; i++) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(String.format("https://www.cheapshark.com/api/1.0/deals?maxAge=%d&pageNumber=%d"
                                , maxAgeDealUptimeInHours, i)))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ArrayList<DealAllPojo> dealAllPojos = jsonMapper.mapArrayOfDealsToJava(response.body());

                DealAllListPojo dealAllListPojo = new DealAllListPojo(dealAllPojos);

                allPages.add(dealAllListPojo);
            }

            String json = objectMapper.writeValueAsString(allPages);


            try (FileWriter fw = new FileWriter("dataFromApi/AllDealsList.txt")) {
                fw.write(json);
            }

            return allPages;

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
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

    public ArrayList<DealAllListPojo> readAllDealsFromFile() {
        String filePath = "dataFromApi/AllDealsList.txt";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (new File(filePath).exists()) {
                String json = new String(Files.readAllBytes(Paths.get(filePath)));
                return objectMapper.readValue(json, new TypeReference<ArrayList<DealAllListPojo>>() {
                });
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
