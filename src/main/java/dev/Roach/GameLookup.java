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

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        ArrayList<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        int gameIdenficator = 0;

        for (GamePojo gamePojo : gamePojos) {
            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
                    gamePojo.getGameID());
            if (transformedGameTitle.equals(game.getTitle())) {
                gameIdenficator = game.getGameID();
            }
        }

        GameDealResponse gameDeal = gamesFetcher.getGameDealObjectUsingID(gameIdenficator);

        System.out.println(gameDeal);
    }


}
