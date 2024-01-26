package dev.Roach.fetchers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StoresFetcher {
    private final HttpClient client;

    public StoresFetcher(HttpClient client) {
        this.client = client;
    }

    public String getAllShops() {
        try (FileWriter fw = new FileWriter("dataFromApi/ShopList.txt")){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/stores"))
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

    public String readAllShopsFromFile() {

        StringBuilder allShops = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("dataFromApi/ShopList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                allShops.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allShops.toString();
    }

}
