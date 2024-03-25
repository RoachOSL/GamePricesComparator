package dev.Roach.datamodel.gameLookup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.Roach.datamodel.GameLookUp;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameLookUpDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        String jsonInput = "{ \"123\": { " +
                "\"info\": { \"title\": \"Test Game\", \"steamAppID\": \"12345\", \"thumb\": \"http://example.com/image.png\" }, " +
                "\"cheapestPriceEver\": { \"price\": \"4.99\", \"date\": 1494806794 }, " +
                "\"deals\": [ { \"storeID\": \"1\", \"dealID\": \"ABCD\", \"price\": \"5.99\", \"retailPrice\": \"9.99\", \"savings\": \"40.04\" } ] " +
                "} }";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GameLookUp.class, new GameLookUpDeserializer());
        mapper.registerModule(module);

        GameLookUp result = mapper.readValue(jsonInput, GameLookUp.class);

        assertNotNull(result);
        assertTrue(result.getGameDealsData().containsKey("123"));

        Map<String, Object> gameData = result.getGameDealsData().get("123");
        assertNotNull(gameData);

        Map<String, String> info = (Map<String, String>) gameData.get("info");
        assertNotNull(info);
        assertEquals("Test Game", info.get("title"));
        assertEquals("12345", info.get("steamAppID"));
        assertEquals("http://example.com/image.png", info.get("thumb"));

        Map<String, Object> cheapestPriceEver = (Map<String, Object>) gameData.get("cheapestPriceEver");
        assertNotNull(cheapestPriceEver);
        assertEquals("4.99", cheapestPriceEver.get("price"));
        assertEquals(1494806794L, cheapestPriceEver.get("date"));

        List<Map<String, String>> deals = (List<Map<String, String>>) gameData.get("deals");
        assertNotNull(deals);
        assertFalse(deals.isEmpty());
        Map<String, String> deal = deals.get(0);
        assertEquals("1", deal.get("storeID"));
        assertEquals("ABCD", deal.get("dealID"));
        assertEquals("5.99", deal.get("price"));
        assertEquals("9.99", deal.get("retailPrice"));
        assertEquals("40.04", deal.get("savings"));
    }
}
