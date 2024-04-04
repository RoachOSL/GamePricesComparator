package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Game {
    private String title;
    private String steamID;
    private double cheapestPrice;
    private int gameID;

    public Game(@JsonProperty("internalName") String title, @JsonProperty("gameID") int gameID, @JsonProperty("steamAppID") String steamID, @JsonProperty("cheapest") double cheapestPrice) {

        this.title = title;
        this.gameID = gameID;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;
    }

    @Override
    public String toString() {
        return "-".repeat(60) + "\n" + "Game Title: " + title + "\n" + "Steam ID: " + (steamID != null ? steamID : "N/A") + "\n" + "Cheapest Price: $" + String.format("%.2f", cheapestPrice) + "\n" + "Game ID: " + gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(cheapestPrice, game.cheapestPrice) == 0 && gameID == game.gameID && Objects.equals(title, game.title) && Objects.equals(steamID, game.steamID);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, steamID, cheapestPrice, gameID);
        result = 37 * result;
        return result;
    }
}
