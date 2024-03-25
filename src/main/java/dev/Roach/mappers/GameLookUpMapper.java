package dev.Roach.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Roach.datamodel.GameLookUp;
import dev.Roach.util.SharedObjectMapper;

import java.util.List;
import java.util.Map;

public class GameLookUpMapper {

    private static final ObjectMapper objectMapper = SharedObjectMapper.getObjectMapper();

    public static GameLookUp mapJSONToGameLookUp(String userJSON) {
        GameLookUp gameLookUp = new GameLookUp();
        try {
            JsonNode node = objectMapper.readTree(userJSON);

            Map<String, Object> infoMap = objectMapper.convertValue(node.get("info"), new TypeReference<Map<String, Object>>() {
            });
            gameLookUp.setInfo(infoMap);

            Map<String, Object> cheapestPriceEverMap = objectMapper.convertValue(node.get("cheapestPriceEver"), new TypeReference<Map<String, Object>>() {
            });
            gameLookUp.setCheapestPriceEver(cheapestPriceEverMap);

            if (node.get("deals").isArray()) {
                List<Map<String, Object>> dealsList = objectMapper.convertValue(node.get("deals"), new TypeReference<List<Map<String, Object>>>() {
                });
                gameLookUp.setDeals(dealsList);
            }
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return gameLookUp;
    }
}
