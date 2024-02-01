package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.fetchers.GamesFetcher;
import lombok.Setter;

import java.util.List;

@Setter
public class GameLookup {

    private GamesFetcher gamesFetcher = new GamesFetcher();

    public GameDealResponse giveTitleToGetListOFDealsWithStores(String gameTitle) {

        if (gameTitle == null || gameTitle.isEmpty()) {
            System.out.println("Game title cannot be null or empty.");
            return new GameDealResponse();
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
            System.out.println("No matches found for the title: " + gameTitle);
            return new GameDealResponse();
        }

        return gamesFetcher.getGameDealObjectUsingID(gameIdentificator);
    }
}
