package dev.Roach.fetchers;

import dev.Roach.JSONMapper;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Setter
public class GamesFetcher {

    private HttpClient client = HttpClient.newBuilder().build();
    private final JSONMapper jsonMapper = new JSONMapper();
    private static final String GAMES_API_URL = "https://www.cheapshark.com/api/1.0/games?";

    public List<GamePojo> getGameContainingKeyword(String keyword) {

        if (keyword == null) {
            throw new NullPointerException("Keyword can't be a null");
        }

        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GAMES_API_URL + "title=" + encodedKeyword))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return jsonMapper.mapArrayOfGamePojoToJava(response.body());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String getGameUsingID(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GAMES_API_URL + "id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var result = response.body();

            if (result.equals("[]")) {
                return "Wrong ID, game doesn't exist";
            }

            return result;

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public GameDealResponse getGameDealObjectUsingID(int id) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GAMES_API_URL + "id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return jsonMapper.mapToGameDealResponse(response.body());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return new GameDealResponse();
        }
    }

}
