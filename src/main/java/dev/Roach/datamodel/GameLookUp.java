package dev.Roach.datamodel;

import dev.Roach.fetchers.StoresFetcher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class GameLookUp {
    private Map<String, Object> info;
    private Map<String, Object> cheapestPriceEver;
    private List<Map<String, Object>> deals = new ArrayList<>();

    private final StoresFetcher storesFetcher = new StoresFetcher();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Store> stores = storesFetcher.readAllShopsFromFile();

        sb.append("Game: ").append(info.getOrDefault("title", "Unknown")).append("\n");
        sb.append("Cheapest Price Ever: ").append(cheapestPriceEver.getOrDefault("price", "Unknown")).append("\n\n");

        sb.append("Deals:\n");
        int dealCount = deals.size();
        for (int i = 0; i < dealCount; i++) {
            Map<String, Object> deal = deals.get(i);
            String storeID = (String) deal.getOrDefault("storeID", "Unknown");
            sb.append("Store: ").append(getStoreNameFromID(storeID, stores)).append("\n");
            sb.append("Deal ID: ").append(deal.getOrDefault("dealID", "Unknown")).append("\n");
            sb.append("Price: ").append(deal.getOrDefault("price", "Unknown")).append(" $\n");
            sb.append("Retail Price: ").append(deal.getOrDefault("retailPrice", "Unknown")).append(" $\n");

            Object savingsObj = deal.get("savings");
            String savings = (savingsObj instanceof String) ? (String) savingsObj : "Unknown";
            sb.append("Savings: ").append(formatSavings(savings));

            if (i < dealCount - 1) {
                sb.append(" $\n\n");
            } else {
                sb.append(" $\n");
            }
        }
        return sb.toString();
    }

    private String getStoreNameFromID(String storeID, List<Store> stores) {
        return stores.stream().filter(store -> store.getId().equals(storeID)).findFirst().map(Store::getName).orElse("Unknown Store ID: " + storeID);
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
        boolean isInfoEmpty = (info == null || info.isEmpty() || info.get("title") == null || info.get("title").toString().isEmpty());
        boolean isCheapestPriceEverEmpty = (cheapestPriceEver == null || cheapestPriceEver.isEmpty() || cheapestPriceEver.get("price") == null || cheapestPriceEver.get("price").toString().isEmpty());
        boolean areDealsEmpty = (deals == null || deals.isEmpty());

        return isInfoEmpty && isCheapestPriceEverEmpty && areDealsEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameLookUp that = (GameLookUp) o;
        return Objects.equals(info, that.info) && Objects.equals(cheapestPriceEver, that.cheapestPriceEver) && Objects.equals(deals, that.deals) && Objects.equals(storesFetcher, that.storesFetcher);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(info, cheapestPriceEver, deals, storesFetcher);
        result = 37 * result;
        return result;
    }
}