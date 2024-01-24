package dev.Roach.pojo.deal;

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
    private String salePrice;
    private String normalPrice;
    private String isOnSale;
    private String savings;
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