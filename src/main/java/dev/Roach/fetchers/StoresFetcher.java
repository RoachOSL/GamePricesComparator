package dev.Roach.fetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.JSONMapper;
import dev.Roach.datamodel.store.StoreAllPojo;
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
public class StoresFetcher {

    private HttpClient client = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String STORES_API_URL = "https://www.cheapshark.com/api/1.0/stores";
    private static final String FILE_PATH_TO_STORES = "dataFromApi/ShopList.txt";

    public List<StoreAllPojo> getAllShops() {

        JSONMapper jsonMapper = new JSONMapper();

        try (FileWriter fw = new FileWriter(FILE_PATH_TO_STORES)) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STORES_API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            fw.write(response.body());

            JsonNode jsonResponse = objectMapper.readTree(response.body());

            if (!jsonResponse.isArray()) {
                return Collections.emptyList();
            }

            return jsonMapper.mapArrayOfAllStoresToJava(response.body());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<StoreAllPojo> readAllShopsFromFile() {

        try {
            if (new File(FILE_PATH_TO_STORES).exists()) {
                String json = new String(Files.readAllBytes(Paths.get(FILE_PATH_TO_STORES)));
                return objectMapper.readValue(json, new TypeReference<ArrayList<StoreAllPojo>>() {
                });
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
