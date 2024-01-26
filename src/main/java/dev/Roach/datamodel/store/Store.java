package dev.Roach.datamodel.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Store {
    private String id;

    private String name;

    private boolean isActive;

    public Store(String id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public static Store createProperStoreObjectByStoreID(List<StoreAllPojo> storeAllPojos, String id) {

        String shopID = "Wrong";
        String name = "Wrong";
        boolean isActive = false;
        for (StoreAllPojo store : storeAllPojos) {
            if (store.getId().equals(id)) {
                shopID = store.getId();
                name = store.getName();
                isActive = store.isActive();

                return new Store(shopID, name, isActive);
            }
        }

        return new Store(shopID, name, isActive);
    }
}
