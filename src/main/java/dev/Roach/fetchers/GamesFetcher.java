package dev.Roach.fetchers;

import dev.Roach.JSONMapper;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GamesFetcher {
    private final HttpClient client;

    public GamesFetcher(HttpClient client) {
        this.client = client;
    }

    public ArrayList<GamePojo> getGameContainingKeyword(String keyword) {

        JSONMapper jsonMapper = new JSONMapper();

        if (keyword == null) {
            throw new NullPointerException("Keyword can't be a null");
        }

        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?title=" + encodedKeyword))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return jsonMapper.mapArrayOfGamePojoToJava(response.body());

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getGameUsingID(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var result = response.body();

            if (result.equals("[]")) {
                return "Wrong ID, game doesn't exist";
            }

            return result;

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public GameDealResponse getGameDealObjectUsingID(int id) {

        JSONMapper jsonMapper = new JSONMapper();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.cheapshark.com/api/1.0/games?id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            return jsonMapper.mapToGameDealResponse(response.body());

        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
            return new GameDealResponse();
        }
    }

}
