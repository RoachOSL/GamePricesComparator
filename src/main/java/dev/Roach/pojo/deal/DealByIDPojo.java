package dev.Roach.pojo.deal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealByIDPojo {
    private GameInfoPojo gameInfo;
    private List<CheaperStoresPojo> cheaperStores;
    private CheapestPricePojo cheapestPrice;
}
