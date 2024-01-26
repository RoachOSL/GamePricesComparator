package dev.Roach.datamodel.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DealByIDPojo {
    private GameInfoPojo gameInfo;
    private List<CheaperStoresPojo> cheaperStores;
    private CheapestPricePojo cheapestPrice;
}
