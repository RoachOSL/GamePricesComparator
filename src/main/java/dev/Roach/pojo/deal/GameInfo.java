package dev.Roach.pojo.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class GameInfo {
    private String storeID;
    private String gameID;
    private String name;
    private String steamAppID;
    private String salePrice;
    private String retailPrice;
    private String steamRatingText;
    private String steamRatingPercent;
    private String steamRatingCount;
    private String metacriticScore;
    private String metacriticLink;
    private long releaseDate;
    private String publisher;
    private String steamworks;
    private String thumb;
}