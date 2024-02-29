package dev.Roach.datamodel.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private String title;
    private String steamID;
    private double cheapestPrice;
    private int gameID;

    public Game(String title, String steamID, double cheapestPrice, int gameID) {
        this.title = title;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;
        this.gameID = gameID;
    }

    @Override
    public String toString() {
        return "-".repeat(60) + "\n" +
                "Game Title: " + title + "\n" +
                "Steam ID: " + (steamID != null ? steamID : "N/A") + "\n" +
                "Cheapest Price: $" + String.format("%.2f", cheapestPrice) + "\n" +
                "Game ID: " + gameID;
    }

}
