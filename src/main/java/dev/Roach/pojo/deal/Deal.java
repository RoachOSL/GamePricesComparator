package dev.Roach.pojo.deal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Deal {
    private String storeID; //DealAllPojo
    private String dealID; //DealAllPojo
    private String savings; //DealAllPojo
    private String price; //CheapestPrice
    private String retailPrice;  //GameInfo


    public Deal(String storeID, String dealID, String savings, String price, String retailPrice) {
        this.storeID = storeID;
        this.dealID = dealID;
        this.savings = savings;
        this.price = price;
        this.retailPrice = retailPrice;
    }

    public static Deal createProperDealObject(DealByIDPojo dealById, DealAllListPojo dealAllListPojo) {
        String storeID = "NotWorking";
        String dealID = "NotWorking";
        String savings = "NotWorking";

        for (List<DealAllPojo> dealAllPojoList : dealAllListPojo.getDeals()) {
            for (DealAllPojo dealAllPojo : dealAllPojoList) {
                if (Objects.equals(dealAllPojo.getGameID(), dealById.getGameInfo().getGameID())) {
                    storeID = dealAllPojo.getStoreID();
                    dealID = dealAllPojo.getDealID();
                    savings = dealAllPojo.getSavings();
                    break;
                }
            }
        }

        String price = dealById.getCheapestPrice().getPrice();
        String retailPrice = dealById.getGameInfo().getRetailPrice();

        return new Deal(storeID, dealID, savings, price, retailPrice);
    }


}
