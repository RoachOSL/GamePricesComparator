package dev.Roach.fetcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GamesFetcher {
    private final HttpClient client;

    public GamesFetcher(HttpClient client) {
        this.client = client;
    }

    public String getGameContainingKeyword(String keyword) {

        if (keyword == null) {
            throw new NullPointerException("Keyword can't be a null");
        }

        try (FileWriter fw = new FileWriter("dataFromApi/GameByContainingKeywordList.txt")) {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?title=" + encodedKeyword))
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

    public String getGameUsingID(int id) {
        try (FileWriter fw = new FileWriter("dataFromApi/GameUsingIDList.txt")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var result = response.body();

            if (result.equals("[]")) {
                return "Wrong ID, game doesn't exist";
            }

            fw.write(result);


            return result;

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }
    public String readGameContainingKeywordFromFile() {

        StringBuilder gameByKeyword = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("dataFromApi/GameByContainingKeywordList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                gameByKeyword.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameByKeyword.toString();
    }

    public String readGameUsingIDFromFile() {

        StringBuilder gameByID = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("dataFromApi/GameUsingIDList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                gameByID.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameByID.toString();
    }
}
