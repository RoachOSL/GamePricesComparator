package dev.Roach.pojo.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class DealAllList {
    private List<DealAllPojo> deals;

    public DealAllList(List<DealAllPojo> deals) {
        this.deals = deals;
    }
}

