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
    private GamesFetcher gamesFetcher = new GamesFetcher();
    private GameLookUpFetcher gameLookUpFetcher = new GameLookUpFetcher();

    public GameLookUp giveTitleToGetListOfDealsWithStores(String gameTitle) {
        if (gameTitle == null || gameTitle.isEmpty()) {
            throw new NoSuchElementException("Game title cannot be null or empty.");
        }

        String transformedGameTitle = gameTitle.toUpperCase().replaceAll("\\s", "");

        List<Game> games = gamesFetcher.getGameContainingKeyword(transformedGameTitle);

        for (Game game : games) {

            String normalizedGameTitle = game.getTitle().toUpperCase().replaceAll("\\s", "");

            if (transformedGameTitle.equals(normalizedGameTitle)) {

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