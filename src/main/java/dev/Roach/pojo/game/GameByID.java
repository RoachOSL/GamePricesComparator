package dev.Roach.pojo.game;

import dev.Roach.pojo.deal.CheapestPricePojo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GameByID {
    private GameInfoPojo info;
    private CheapestPricePojo cheapestPriceEver;
    private List<GameDealByIDPojo> deals;
}
