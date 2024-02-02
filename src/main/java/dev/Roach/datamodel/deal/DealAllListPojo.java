package dev.Roach.datamodel.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DealAllListPojo {
    private List<DealAllPojo> deals;

    public DealAllListPojo() {
    }

    public DealAllListPojo(List<DealAllPojo> deals) {
        this.deals = deals;
    }
}