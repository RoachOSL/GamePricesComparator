package mappers;

import dev.Roach.datamodel.GameLookUp;
import dev.Roach.mappers.GameLookUpMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameLookUpMapperTest {

    private final GameLookUpMapper gameLookUpMapper = new GameLookUpMapper();

    @Test
    public void mapJSONToGameLookUpForSuccessOfProperInput() {
        String json = """
                     {
                         "info": {
                             "title": "Game Title 1",
                             "steamAppID": "12345",
                             "thumb": "https://example.com/game1/thumb.jpg"
                         },
                         "cheapestPriceEver": {
                             "price": "14.99",
                             "date": 1596240000
                         },
                         "deals": [
                             {
                                 "storeID": "store1",
                                 "dealID": "deal1",
                                 "price": "19.99",
                                 "retailPrice": "29.99",
                                 "savings": "33.34"
                             },
                             {
                                 "storeID": "store2",
                                 "dealID": "deal2",
                                 "price": "17.99",
                                 "retailPrice": "27.99",
                                 "savings": "35.73"
                             }
                         ]
                     }
                """;

        GameLookUp result = gameLookUpMapper.mapJSONToGameLookUp(json);

        Map<String, Object> info = result.getInfo();
        assertEquals("Game Title 1", info.get("title"));
        assertEquals("12345", info.get("steamAppID"));
        assertEquals("https://example.com/game1/thumb.jpg", info.get("thumb"));


        Map<String, Object> cheapestPriceEver = result.getCheapestPriceEver();
        assertEquals("14.99", cheapestPriceEver.get("price").toString());
        assertEquals(1596240000, Integer.parseInt(cheapestPriceEver.get("date").toString()));


        List<Map<String, Object>> deals = result.getDeals();
        assertEquals(2, deals.size());

        Map<String, Object> deal1 = deals.get(0);
        assertEquals("store1", deal1.get("storeID"));
        assertEquals("deal1", deal1.get("dealID"));
        assertEquals("19.99", deal1.get("price").toString());
        assertEquals("29.99", deal1.get("retailPrice").toString());
        assertEquals("33.34", deal1.get("savings").toString());

        Map<String, Object> deal2 = deals.get(1);
        assertEquals("store2", deal2.get("storeID"));
        assertEquals("deal2", deal2.get("dealID"));
        assertEquals("17.99", deal2.get("price").toString());
        assertEquals("27.99", deal2.get("retailPrice").toString());
        assertEquals("35.73", deal2.get("savings").toString());
    }

    @Test
    public void mapJSONToGameLookUpForEmptyJson() {
        String json = "[]";

        GameLookUp result = gameLookUpMapper.mapJSONToGameLookUp(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void mapJSONToGameLookUpWithMissingSections() {
        String jsonMissingSections = """
                {
                    "info": {
                        "title": "Game Title 1",
                        "steamAppID": "12345",
                        "thumb": "https://example.com/game1/thumb.jpg"
                    }
                }
                """;

        GameLookUp result = gameLookUpMapper.mapJSONToGameLookUp(jsonMissingSections);

        assertNotNull(result.getInfo());
        assertTrue(result.getCheapestPriceEver().isEmpty());
        assertTrue(result.getDeals().isEmpty());
    }

}
