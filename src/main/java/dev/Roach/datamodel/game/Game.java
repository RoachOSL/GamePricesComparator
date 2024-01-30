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


    public Game(String title, String steamID, double cheapestPrice) {
        this.title = title;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;

    }

}
