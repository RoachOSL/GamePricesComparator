package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.fetchers.GamesFetcher;

import java.net.http.HttpClient;
import java.util.ArrayList;

public class GameLookup {
    HttpClient client = HttpClient.newBuilder().build();
    GamesFetcher gamesFetcher = new GamesFetcher(client);


    public void giveTitleToGetListOFDealsWithStores(String gameTitle) {

        if (gameTitle == null || gameTitle.isEmpty()) {
            System.out.println("Game title cannot be null or empty.");
            return;
        }

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        ArrayList<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        int gameIdentificator = 0;
        boolean gameFound = false;

        for (GamePojo gamePojo : gamePojos) {
            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
                    gamePojo.getGameID());
            if (transformedGameTitle.equals(game.getTitle())) {
                gameIdentificator = game.getGameID();
                gameFound = true;
                break;
            }
        }

        if (!gameFound) {
            System.out.println("No exact match found for the title: " + gameTitle);
            return;
        }

        GameDealResponse gameDeal = gamesFetcher.getGameDealObjectUsingID(gameIdentificator);

        System.out.println(gameDeal);
    }
}
