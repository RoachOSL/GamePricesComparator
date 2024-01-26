package dev.Roach.datamodel.deal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealAllListPojo {
    private List<List<DealAllPojo>> deals;
}

