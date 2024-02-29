package dev.Roach.datamodel.gameLookup;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class GameDealResponse {
    private GameInfo info;
    private CheapestPrice cheapestPriceEver;
    private List<LookupDeal> deals;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Game: ").append(info.getTitle()).append("\n");
        sb.append("Cheapest Price Ever: ").append(cheapestPriceEver.getPrice()).append("\n\n");

        sb.append("Deals:\n");
        for (LookupDeal deal : deals) {
            sb.append("Store ID: ").append(deal.getStoreID()).append("\n");
            sb.append("Deal ID: ").append(deal.getDealID()).append("\n");
            sb.append("Price: ").append(deal.getPrice()).append("\n");
            sb.append("Retail Price: ").append(deal.getRetailPrice()).append("\n");
            sb.append("Savings: ").append(deal.getSavings()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean isEmpty() {
        return (info == null || info.getTitle() == null || info.getTitle().isEmpty())
                && (cheapestPriceEver == null || cheapestPriceEver.getPrice().isEmpty())
                && (deals == null || deals.isEmpty());
    }
}