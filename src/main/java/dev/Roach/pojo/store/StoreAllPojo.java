package dev.Roach.pojo.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreAllPojo {

    @JsonProperty("storeID")
    private String id;
    @JsonProperty("storeName")
    private String name;
    @JsonProperty("isActive")
    private boolean isActive;
    private ImagePojo images;

}
