package dev.Roach.fetchers;

import dev.Roach.datamodel.GameLookUp;
import dev.Roach.mappers.GameLookUpMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GameLookUpFetcher {
    private HttpClient client = HttpClient.newBuilder().build();
    private static final String GAMES_API_URL = "https://www.cheapshark.com/api/1.0/games?";

    public GameLookUp getGameLookUpObjectUsingID(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GAMES_API_URL + "id=" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return GameLookUpMapper.mapJSONToGameLookUp(response.body());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return new GameLookUp();
        }
    }
}
