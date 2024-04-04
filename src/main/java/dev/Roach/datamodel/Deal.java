package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Deal {
    private String storeID;
    private String dealID;
    private double savings;
    private String price;
    private String retailPrice;

    public Deal(@JsonProperty("storeID") String storeID, @JsonProperty("dealID") String dealID, @JsonProperty("salePrice") String price, @JsonProperty("normalPrice") String retailPrice, @JsonProperty("savings") double savings) {
        this.storeID = storeID;
        this.dealID = dealID;
        this.price = price;
        this.retailPrice = retailPrice;
        this.savings = savings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return Double.compare(savings, deal.savings) == 0 && Objects.equals(storeID, deal.storeID) && Objects.equals(dealID, deal.dealID) && Objects.equals(price, deal.price) && Objects.equals(retailPrice, deal.retailPrice);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(storeID, dealID, savings, price, retailPrice);
        result = 37 * result;
        return result;
    }
}
