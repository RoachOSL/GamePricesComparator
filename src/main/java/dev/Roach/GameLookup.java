package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.fetchers.GamesFetcher;

import java.util.List;
import java.util.NoSuchElementException;

public class GameLookup {
    HttpClient client = HttpClient.newBuilder().build();
    GamesFetcher gamesFetcher = new GamesFetcher(client);


    public void giveTitleToGetListOFDealsWithStores(String gameTitle) {

        if (gameTitle == null || gameTitle.isEmpty()) {
            throw new NoSuchElementException("Game title cannot be null or empty.");
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

        if (!foundGame) {
            throw new NoSuchElementException("No game deals found for the title: " + gameTitle);
        }

        GameDealResponse response = gamesFetcher.getGameDealObjectUsingID(gameIdentificator);

        if (response.isEmpty()) {
            throw new NoSuchElementException("Game deal is empty for followed ID: " + gameIdentificator);
        }

        return response;
    }
}
