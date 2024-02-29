package dev.Roach.datamodel.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class GamePojo {
    @JsonProperty("gameID")
    private int gameID;
    @JsonProperty("steamAppID")
    private String steamID;
    @JsonProperty("cheapest")
    private double cheapestPrice;
    private String cheapestDealID;
    private String external;
    @JsonProperty("internalName")
    private String title;
    private String thumb;
}
