package dev.Roach.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.GameLookUp;
import dev.Roach.util.SharedObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLookUpMapper {
    private final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();

    public GameLookUp mapJSONToGameLookUp(String userJSON) {
        GameLookUp gameLookUp = new GameLookUp();
        try {
            JsonNode node = objectMapper.readTree(userJSON);

            if (node.has("info") && !node.get("info").isNull()) {
                Map<String, Object> infoMap = objectMapper.convertValue(node.get("info"), new TypeReference<Map<String, Object>>() {
                });
                gameLookUp.setInfo(infoMap);
            } else {
                gameLookUp.setInfo(new HashMap<>());
            }


            if (node.has("cheapestPriceEver") && !node.get("cheapestPriceEver").isNull()) {
                Map<String, Object> cheapestPriceEverMap = objectMapper.convertValue(node.get("cheapestPriceEver"), new TypeReference<Map<String, Object>>() {
                });
                gameLookUp.setCheapestPriceEver(cheapestPriceEverMap);
            } else {
                gameLookUp.setCheapestPriceEver(new HashMap<>());
            }

            if (node.has("deals") && !node.get("deals").isNull() && node.get("deals").isArray()) {
                List<Map<String, Object>> dealsList = objectMapper.convertValue(node.get("deals"), new TypeReference<List<Map<String, Object>>>() {
                });
                gameLookUp.setDeals(dealsList);
            } else {
                gameLookUp.setDeals(new ArrayList<>());
            }

        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return gameLookUp;
    }
}
