package dev.Roach.fetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.JSONMapper;
import dev.Roach.datamodel.deal.DealAllListPojo;
import dev.Roach.datamodel.deal.DealAllPojo;
import lombok.Setter;

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
import java.util.Collections;
import java.util.List;

@Setter
public class DealsFetcher {

    private HttpClient client = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String dealsApiUrl = "https://www.cheapshark.com/api/1.0/deals?";
    private static final String filePathToAllDeals = "dataFromApi/AllDealsList.txt";


    public List<DealAllListPojo> getAllDeals() {

        ArrayList<DealAllListPojo> allPages = new ArrayList<>();
        JSONMapper jsonMapper = new JSONMapper();

        HttpRequest initialRequest = HttpRequest.newBuilder()
                .uri(URI.create(dealsApiUrl + "pageNumber=0"))
                .GET()
                .build();
        try {

            HttpResponse<String> initialResponse = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());
            int totalPages = Integer.parseInt(initialResponse.headers().firstValue("X-Total-Page-Count").get());

            int maxAgeDealUptimeInHours = 240;

            for (int i = 0; i < totalPages; i++) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(String.format(dealsApiUrl + "maxAge=%d&pageNumber=%d"
                                , maxAgeDealUptimeInHours, i)))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                List<DealAllPojo> dealAllPojos = jsonMapper.mapArrayOfDealsToJava(response.body());

                DealAllListPojo dealAllListPojo = new DealAllListPojo(dealAllPojos);

                allPages.add(dealAllListPojo);
            }

            String json = objectMapper.writeValueAsString(allPages);

            try (FileWriter fw = new FileWriter(filePathToAllDeals)) {
                fw.write(json);
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
                    .uri(URI.create(dealsApiUrl + "id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public List<DealAllListPojo> readAllDealsFromFile() {

        try {
            if (new File(filePathToAllDeals).exists()) {
                String json = new String(Files.readAllBytes(Paths.get(filePathToAllDeals)));
                return objectMapper.readValue(json, new TypeReference<ArrayList<DealAllListPojo>>() {
                });
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
