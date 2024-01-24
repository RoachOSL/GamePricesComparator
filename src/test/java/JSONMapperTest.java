import dev.Roach.JSONMapper;
import dev.Roach.pojo.deal.DealAllPojo;
import dev.Roach.pojo.deal.DealByIDPojo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JSONMapperTest {

    private final JSONMapper jsonMapper = new JSONMapper();

//    @Test
//    public void testMapArrayToJava_Success() {
//
//        List<String> jsonList = Arrays.asList(
//                "{\"dealID\":\"deal1\"}",
//                "{\"dealID\":\"deal2\"}"
//        );
//
//        List<DealAllPojo> result = jsonMapper.mapArrayToJava(jsonList, DealAllPojo.class);
//
//        assertEquals(2, result.size());
//        assertEquals("deal1", result.get(0).getDealID());
//        assertEquals("deal2", result.get(1).getDealID());
//    }


    @Test
    public void testMapToJava_DealByIDPojo_Success() {
        String json = """
                {
                  "gameInfo": {
                    "storeID": "1",
                    "gameID": "123",
                    "name": "Sample Game",
                    "steamAppID": "456",
                    "salePrice": "29.99",
                    "retailPrice": "59.99",
                    "steamRatingText": "Very Positive",
                    "steamRatingPercent": "80",
                    "steamRatingCount": "1000",
                    "metacriticScore": "85",
                    "metacriticLink": "/game/pc/sample-game",
                    "releaseDate": 1609459200,
                    "publisher": "Sample Publisher",
                    "steamworks": "Yes",
                    "thumb": "/thumbs/sample-game.jpg"
                  },
                  "cheaperStores": [
                    {
                      "dealID": "deal123",
                      "storeID": "2",
                      "salePrice": "19.99",
                      "retailPrice": "49.99"
                    }
                  ],
                  "cheapestPrice": {
                    "price": "18.99",
                    "date": 1609545600
                  }
                }
                """;

        DealByIDPojo result = jsonMapper.mapToJava(json, DealByIDPojo.class);

        assertNotNull(result);
        assertNotNull(result.getGameInfo());
        assertEquals("1", result.getGameInfo().getStoreID());
        assertEquals("123", result.getGameInfo().getGameID());
        assertEquals("Sample Game", result.getGameInfo().getName());


        assertNotNull(result.getCheaperStores());
        assertFalse(result.getCheaperStores().isEmpty());
        assertEquals("deal123", result.getCheaperStores().get(0).getDealID());


        assertNotNull(result.getCheapestPrice());
        assertEquals("18.99", result.getCheapestPrice().getPrice());
    }
}

