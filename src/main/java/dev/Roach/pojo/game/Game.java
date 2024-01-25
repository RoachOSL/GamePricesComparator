package dev.Roach.pojo.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Game {

    private String title;

    private String steamID;
    private double cheapestPrice;


    public Game(String title, String steamID, double cheapestPrice) {
        this.title = title;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;

    }

    public static Game createProperGameObjectByContaingKeyword(List<GamePojo> storeGamePojo, String gameID) {

        String title = "Wrong";
        String steamID = "Wrong";
        double cheapestPrice = 0;

        for (GamePojo game: storeGamePojo) {
            if (game.getGameID().equals(gameID)) {

                title = game.getTitle();
                steamID = game.getSteamID();
                cheapestPrice = game.getCheapestPrice();
                return new Game(title, steamID, cheapestPrice);
            }
        }

        return new Game(title, steamID, cheapestPrice);
    }
}
