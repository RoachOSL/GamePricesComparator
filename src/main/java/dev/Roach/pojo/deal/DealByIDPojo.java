package dev.Roach.pojo.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString


public class DealByIDPojo {
    private GameInfo gameInfo;
    private List<CheaperStores> cheaperStores;
    private CheapestPrice cheapestPrice;
}
