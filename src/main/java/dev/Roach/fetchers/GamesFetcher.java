package dev.Roach.fetchers;

import dev.Roach.datamodel.Game;
import dev.Roach.mappers.GameMapper;
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
    private final GameMapper gameMapper = new GameMapper();
    private static final String GAMES_API_URL = "https://www.cheapshark.com/api/1.0/games?";

    public List<Game> getGameContainingKeyword(String keyword) {
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

            return gameMapper.mapArrayOfGamePojoToJava(response.body());

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

            String result = response.body();

            if (result.equals("[]")) {
                return "Wrong ID, game doesn't exist";
            }

            return result;

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }


}