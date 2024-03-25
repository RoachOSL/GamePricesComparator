package dev.Roach.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Store {
    private String id;
    private String name;
    private boolean isActive;

    public Store(@JsonProperty("storeID") String id,
                 @JsonProperty("storeName") String name,
                 @JsonProperty("isActive") boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }
}
