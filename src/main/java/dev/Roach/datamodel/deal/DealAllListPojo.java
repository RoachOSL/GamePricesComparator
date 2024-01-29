package dev.Roach.datamodel.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class DealAllListPojo {
    private ArrayList<DealAllPojo> deals;

    public DealAllListPojo(ArrayList<DealAllPojo> deals) {
        this.deals = deals;
    }
}

