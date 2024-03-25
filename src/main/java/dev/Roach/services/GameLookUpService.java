package dev.Roach.services;

import dev.Roach.datamodel.Game;
import dev.Roach.datamodel.GameLookUp;
import dev.Roach.fetchers.GameLookUpFetcher;
import dev.Roach.fetchers.GamesFetcher;
import lombok.Setter;

import java.util.List;
import java.util.NoSuchElementException;

@Setter
public class GameLookUpService {
    private final GamesFetcher gamesFetcher = new GamesFetcher();
    private final GameLookUpFetcher gameLookUpFetcher = new GameLookUpFetcher();

    public GameLookUp giveTitleToGetListOFDealsWithStores(String gameTitle) {
        if (gameTitle == null || gameTitle.isEmpty()) {
            throw new NoSuchElementException("Game title cannot be null or empty.");
        }

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        List<Game> games = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        for (Game game : games) {
            if (transformedGameTitle.equals(game.getTitle())) {

                GameLookUp response = gameLookUpFetcher.getGameLookUpObjectUsingID(game.getGameID());

                if (response.isEmpty()) {
                    throw new NoSuchElementException("Game deal is empty for followed ID: " + game.getGameID());
                }

                return response;
            }
        }

        throw new NoSuchElementException("No game deals found for the title: " + gameTitle);
    }
}