package dev.Roach.datamodel.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

}
