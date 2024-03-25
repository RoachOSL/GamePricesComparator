package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private String title;
    private String steamID;
    private double cheapestPrice;
    private int gameID;

    public Game(@JsonProperty("internalName") String title,
                @JsonProperty("gameID") int gameID,
                @JsonProperty("steamAppID") String steamID,
                @JsonProperty("cheapest") double cheapestPrice) {

        this.title = title;
        this.gameID = gameID;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;
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
