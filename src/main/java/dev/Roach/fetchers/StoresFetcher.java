package dev.Roach.fetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.Store;
import dev.Roach.mappers.StoresMapper;
import dev.Roach.util.SharedObjectMapper;
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
    private static final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();
    private static final String STORES_API_URL = "https://www.cheapshark.com/api/1.0/stores";
    private static final String FILE_PATH_TO_STORES = "src/main/resources/ShopList.txt";


    public List<Store> getAllShopsAndWriteItToTheFile() {
        StoresMapper jsonMapper = new StoresMapper();
        String storeData = fetchStoreData();
        if (storeData == null) {
            return Collections.emptyList();
        }
        writeDataToFile(storeData);

        try {
            JsonNode jsonResponse = objectMapper.readTree(storeData);

            if (!jsonResponse.isArray()) {
                return Collections.emptyList();
            }

            return jsonMapper.mapArrayOfAllStoresToJava(storeData);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String fetchStoreData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STORES_API_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void writeDataToFile(String data) {
        try (FileWriter fw = new FileWriter(FILE_PATH_TO_STORES)) {
            fw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Store> readAllShopsFromFile() {
        try {
            if (new File(FILE_PATH_TO_STORES).exists() && isFileRecent()) {
                String json = new String(Files.readAllBytes(Paths.get(FILE_PATH_TO_STORES)));
                return objectMapper.readValue(json, new TypeReference<ArrayList<Store>>() {
                });
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    private boolean isFileRecent() {
        File file = new File(FILE_PATH_TO_STORES);
        if (!file.exists()) {
            return false;
        }
        long lastModified = file.lastModified();
        long daysInMillis = 3 * 24 * 60 * 60 * 1000L;
        return (System.currentTimeMillis() - lastModified) <= daysInMillis;
    }

}