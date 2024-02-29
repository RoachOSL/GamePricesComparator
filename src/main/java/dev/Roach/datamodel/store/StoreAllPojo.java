package dev.Roach.datamodel.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreAllPojo {
    @JsonProperty("storeID")
    private String id;
    @JsonProperty("storeName")
    private String name;
    @JsonProperty("isActive")
    private boolean isActive;
    private ImagePojo images;
}
