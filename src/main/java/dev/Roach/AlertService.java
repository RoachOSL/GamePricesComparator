package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.fetchers.GamesFetcher;
import lombok.Setter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Setter
public class AlertService {

    private HttpClient httpClient = HttpClient.newBuilder().build();

    private GamesFetcher gamesFetcher = new GamesFetcher();

    public boolean createOrUpdateAlert(String email, int gameID, double price) {
        String url = String.format("https://www.cheapshark.com/api/1.0/alerts?action=set&email=%s&gameID=%d&price=%.2f",
                email, gameID, price);
        return sendAlertRequest(url);
    }

    public boolean deleteAlert(String email, int gameID) {
        String url = String.format("https://www.cheapshark.com/api/1.0/alerts?action=delete&email=%s&gameID=%d",
                email, gameID);
        return sendAlertRequest(url);
    }

    private boolean sendAlertRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Boolean.parseBoolean(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public String getAlertsForEmail(String email) {

        String url = String.format("https://www.cheapshark.com/api/1.0/alerts?action=manage&email=%s", email);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createOrUpdateAlertWithGameTitle(String gameTitle, String email, double price) {

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        List<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        int gameID = 0;

        for (GamePojo gamePojo : gamePojos) {
            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
                    gamePojo.getGameID());
            if (gamePojo.getTitle().equals(transformedGameTitle)) {
                gameID = game.getGameID();
            }
        }

        String url = String.format("https://www.cheapshark.com/api/1.0/alerts?action=set&email=%s&gameID=%d&price=%.2f",
                email, gameID, price);

        return sendAlertRequest(url);
    }
}


