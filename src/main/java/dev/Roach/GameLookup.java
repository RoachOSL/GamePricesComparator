package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.fetchers.GamesFetcher;
import lombok.Setter;

import java.util.List;
import java.util.NoSuchElementException;

@Setter
public class GameLookup {

    private GamesFetcher gamesFetcher = new GamesFetcher();

    public GameDealResponse giveTitleToGetListOFDealsWithStores(String gameTitle) {

        if (gameTitle == null || gameTitle.isEmpty()) {
            throw new NoSuchElementException("Game title cannot be null or empty.");
        }

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        List<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        int gameIdentificator = 0;
        boolean foundGame = false;

        for (GamePojo gamePojo : gamePojos) {
            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
                    gamePojo.getGameID());
            if (transformedGameTitle.equals(game.getTitle())) {
                gameIdentificator = game.getGameID();
                foundGame = true;
                break;
            }
        }

        if (!foundGame) {
            throw new NoSuchElementException("No game deals found for the title: " + gameTitle);
        }

        GameDealResponse response = gamesFetcher.getGameDealObjectUsingID(gameIdentificator);

        if (response.isEmpty()) {
            throw new NoSuchElementException("GameDealResponse is empty for game ID: " + gameIdentificator);
        }

        return response;
    }
}
