package dev.Roach.fetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.JSONMapper;
import dev.Roach.datamodel.store.StoreAllPojo;

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

public class StoresFetcher {
    private final HttpClient client;

    public StoresFetcher(HttpClient client) {
        this.client = client;
    }

    public ArrayList<StoreAllPojo> getAllShops() {

        JSONMapper jsonMapper = new JSONMapper();
        ObjectMapper objectMapper = new ObjectMapper();

        try (FileWriter fw = new FileWriter("dataFromApi/ShopList.txt")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/stores"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            fw.write(response.body());

            JsonNode jsonResponse = objectMapper.readTree(response.body());

            if (!jsonResponse.isArray()) {
                return new ArrayList<>();
            }

            return jsonMapper.mapArrayOfAllStoresToJava(response.body());

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<StoreAllPojo> readAllShopsFromFile() {

        String filePath = "dataFromApi/ShopList.txt";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (new File(filePath).exists()) {
                String json = new String(Files.readAllBytes(Paths.get(filePath)));
                return objectMapper.readValue(json, new TypeReference<ArrayList<StoreAllPojo>>() {
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
