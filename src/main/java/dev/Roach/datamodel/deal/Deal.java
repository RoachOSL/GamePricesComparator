package dev.Roach.datamodel.deal;

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


    public Deal(String storeID, String dealID, String price, String retailPrice, double savings) {
        this.storeID = storeID;
        this.dealID = dealID;
        this.price = price;
        this.retailPrice = retailPrice;
        this.savings = savings;
    }


}
