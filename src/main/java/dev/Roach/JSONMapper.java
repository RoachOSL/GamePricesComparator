package dev.Roach;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.deal.DealAllPojo;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.store.StoreAllPojo;

import java.util.ArrayList;


public class JSONMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


      public ArrayList<GamePojo> mapArrayOfGamePojoToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<GamePojo>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DealAllPojo> mapArrayOfDealsToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<DealAllPojo>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<StoreAllPojo> mapArrayOfAllStoresToJava(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<ArrayList<StoreAllPojo>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GameDealResponse mapToGameDealResponse(String json) {
        try {
            return objectMapper.readValue(json, GameDealResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}