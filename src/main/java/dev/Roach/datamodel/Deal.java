package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Deal {
    private String storeID;
    private String dealID;
    private double savings;
    private String price;
    private String retailPrice;

    public Deal(@JsonProperty("storeID") String storeID,
                @JsonProperty("dealID") String dealID,
                @JsonProperty("salePrice") String price,
                @JsonProperty("normalPrice") String retailPrice,
                @JsonProperty("savings") double savings) {
        this.storeID = storeID;
        this.dealID = dealID;
        this.price = price;
        this.retailPrice = retailPrice;
        this.savings = savings;
    }
}
