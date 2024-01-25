package dev.Roach.pojo.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class GamePojo {

    private String gameID;
    @JsonProperty("steamAppID")
    private int steamID;
    @JsonProperty("cheapest")
    private double cheapestPrice;
    private String cheapestDealID;
    private String external;
    @JsonProperty("internalName")
    private String title;
    private String thumb;



}
