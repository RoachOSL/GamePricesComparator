package dev.Roach.pojo.game;

public class Game {

    private String title;

    private int steamID;
    private double cheapestPrice;


    public Game(String title, int steamID, double cheapestPrice) {
        this.title = title;
        this.steamID = steamID;
        this.cheapestPrice = cheapestPrice;

    }

    public static Game createProperGameObject(GamePojo gamePojo) {
        return new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice());
    }
}
