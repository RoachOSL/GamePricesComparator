package dev.Roach.datamodel.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameDealByIDPojo {
    private String storeID;
    private String dealID;
    private String price;
    private String retailPrice;
    private String savings;
}
