package dev.Roach.pojo.deal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString


@JsonIgnoreProperties(ignoreUnknown = true)
public class DealByIDPojo {
    private GameInfo gameInfo;
    private List<CheaperStores> cheaperStores;
    private CheapestPrice cheapestPrice;
}
