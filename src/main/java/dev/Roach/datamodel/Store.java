package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Store {
    private String id;
    private String name;
    private boolean isActive;

    public Store(@JsonProperty("storeID") String id, @JsonProperty("storeName") String name, @JsonProperty("isActive") boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return isActive == store.isActive && Objects.equals(id, store.id) && Objects.equals(name, store.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, isActive);
        result = 37 * result;
        return result;
    }
}
