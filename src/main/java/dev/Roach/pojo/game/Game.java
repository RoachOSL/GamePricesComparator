package dev.Roach.pojo.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Game {

    @JsonProperty("internalName")
    private String title;
    @JsonProperty("steamAppID")
    private int steamID;
    @JsonProperty("cheapest")
    private double cheapestPrice;

}
