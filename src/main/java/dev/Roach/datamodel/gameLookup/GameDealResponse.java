package dev.Roach.datamodel.gameLookup;

import dev.Roach.datamodel.store.StoreAllPojo;
import dev.Roach.fetchers.StoresFetcher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameDealResponse {
    private GameInfo info;
    private CheapestPrice cheapestPriceEver;
    private List<LookupDeal> deals;
    private final StoresFetcher storesFetcher = new StoresFetcher();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<StoreAllPojo> stores = storesFetcher.readAllShopsFromFile();

        sb.append("Game: ").append(info.getTitle()).append("\n");
        sb.append("Cheapest Price Ever: ").append(cheapestPriceEver.getPrice()).append("\n\n");

        sb.append("Deals:\n");
        for (LookupDeal deal : deals) {
            sb.append("Store: ").append(getStoreNameFromID(deal.getStoreID(), stores)).append("\n");
            sb.append("Deal ID: ").append(deal.getDealID()).append("\n");
            sb.append("Price: ").append(deal.getPrice()).append(" $\n");
            sb.append("Retail Price: ").append(deal.getRetailPrice()).append(" $\n");
            sb.append("Savings: ").append(formatSavings(deal.getSavings())).append(" $\n\n");
        }
        return sb.toString();
    }

    private String getStoreNameFromID(String storeID, List<StoreAllPojo> stores) {
        return stores.stream()
                .filter(store -> store.getId().equals(storeID))
                .findFirst()
                .map(StoreAllPojo::getName)
                .orElse("Unknown Store ID: " + storeID);
    }

    private String formatSavings(String savingsStr) {
        try {
            double savings = Double.parseDouble(savingsStr);
            return String.format("%.2f", savings);
        } catch (NumberFormatException e) {
            return savingsStr + " (Invalid number format)";
        }
    }

    public boolean isEmpty() {
        return (info == null || info.getTitle() == null || info.getTitle().isEmpty())
                && (cheapestPriceEver == null || cheapestPriceEver.getPrice().isEmpty())
                && (deals == null || deals.isEmpty());
    }
}