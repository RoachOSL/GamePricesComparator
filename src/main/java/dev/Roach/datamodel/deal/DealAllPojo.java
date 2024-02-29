package dev.Roach.datamodel.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DealAllPojo {
    private String internalName;
    private String title;
    private String metacriticLink;
    private String dealID;
    private String storeID;
    private String gameID;
    @JsonProperty("salePrice")
    private String price;
    @JsonProperty("normalPrice")
    private String retailPrice;
    private String isOnSale;
    @JsonProperty("savings")
    private double savings;
    private String metacriticScore;
    private String steamRatingText;
    private String steamRatingPercent;
    private String steamRatingCount;
    private String steamAppID;
    private long releaseDate;
    private long lastChange;
    private String dealRating;
    private String thumb;

}
