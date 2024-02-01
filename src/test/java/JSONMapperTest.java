import dev.Roach.JSONMapper;
import dev.Roach.datamodel.deal.DealAllPojo;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.CheapestPrice;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.gameLookup.GameInfo;
import dev.Roach.datamodel.gameLookup.LookupDeal;
import dev.Roach.datamodel.store.StoreAllPojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JSONMapperTest {

    private final JSONMapper mockJsonMapper = mock(JSONMapper.class);

    @Test
    public void mapArrayOfGamePojoToJavaTestForSuccessOfProperInput() {

        String json = """
                [
                    {
                        "gameID": 12345,
                        "steamAppID": "67890",
                        "cheapest": 19.99,
                        "cheapestDealID": "deal123",
                        "external": "Game Title External",
                        "internalName": "Game Title Internal",
                        "thumb": "https://example.com/thumb.jpg"
                    }
                ]
                """;

        ArrayList<GamePojo> gamePojoTestingArray = new ArrayList<>();
        GamePojo pojo = new GamePojo();

        pojo.setGameID(12345);
        pojo.setExternal("Game Title External");
        pojo.setCheapestDealID("deal123");

        gamePojoTestingArray.add(pojo);

        when(mockJsonMapper.mapArrayOfGamePojoToJava(json)).thenReturn(gamePojoTestingArray);

        List<GamePojo> result = mockJsonMapper.mapArrayOfGamePojoToJava(json);

        assertEquals(1, result.size());
        assertEquals(12345, result.getFirst().getGameID());
        assertEquals("deal123", result.getFirst().getCheapestDealID());
        assertEquals("Game Title External", result.getFirst().getExternal());
    }

    @Test
    public void mapArrayOfGamePojoToJavaTestForEmptyJson() {
        String json = "[]";

        ArrayList<GamePojo> expectedResult = new ArrayList<>();
        when(mockJsonMapper.mapArrayOfGamePojoToJava(json)).thenReturn(expectedResult);

        List<GamePojo> result = mockJsonMapper.mapArrayOfGamePojoToJava(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void mapArrayOfDealsToJavaTestForSuccessOfProperInput() {

        String json = """
                [
                    {
                        "internalName": "Game Internal Name 1",
                        "title": "Game Title 1",
                        "metacriticLink": "/game/pc/game-title-1",
                        "dealID": "deal1234",
                        "storeID": "store5678",
                        "gameID": "game9012",
                        "salePrice": "29.99",
                        "normalPrice": "59.99",
                        "isOnSale": "yes",
                        "savings": 50.00,
                        "metacriticScore": "85",
                        "steamRatingText": "Very Positive",
                        "steamRatingPercent": "88",
                        "steamRatingCount": "10000",
                        "steamAppID": "123456",
                        "releaseDate": 1577836800,
                        "lastChange": 1619827200,
                        "dealRating": "9.5",
                        "thumb": "https://example.com/thumb1.jpg"
                    }
                ]
                """;

        ArrayList<DealAllPojo> dealAllPojoTestingArray = new ArrayList<>();

        DealAllPojo dealAllPojo = new DealAllPojo();

        dealAllPojo.setInternalName("Game Internal Name 1");
        dealAllPojo.setIsOnSale("yes");
        dealAllPojo.setDealID("deal1234");
        dealAllPojo.setLastChange(1619827200);
        dealAllPojo.setDealRating("9.5");

        dealAllPojoTestingArray.add(dealAllPojo);

        when(mockJsonMapper.mapArrayOfDealsToJava(json)).thenReturn(dealAllPojoTestingArray);

        List<DealAllPojo> result = mockJsonMapper.mapArrayOfDealsToJava(json);

        assertEquals(1, result.size());
        assertEquals("Game Internal Name 1", result.getFirst().getInternalName());
        assertEquals("yes", result.getFirst().getIsOnSale());
        assertEquals("deal1234", result.getFirst().getDealID());
        assertEquals(1619827200, result.getFirst().getLastChange());
        assertEquals("9.5", result.getFirst().getDealRating());
    }

    @Test
    public void mapArrayOfDealsToJavaTestForEmptyJson() {

        String json = "[]";

        ArrayList<DealAllPojo> expectedResult = new ArrayList<>();
        when(mockJsonMapper.mapArrayOfDealsToJava(json)).thenReturn(expectedResult);

        List<DealAllPojo> result = mockJsonMapper.mapArrayOfDealsToJava(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForSuccessOfProperInput() {

        String json = """
                [
                    {
                        "storeID": "store001",
                        "storeName": "Game Store Alpha",
                        "isActive": true,
                        "images": {
                            "banner": "https://example.com/banner1.jpg",
                            "logo": "https://example.com/logo1.jpg",
                            "icon": "https://example.com/icon1.png"
                        }
                    },
                    {
                        "storeID": "store002",
                        "storeName": "Game Store Beta",
                        "isActive": false,
                        "images": {
                            "banner": "https://example.com/banner2.jpg",
                            "logo": "https://example.com/logo2.jpg",
                            "icon": "https://example.com/icon2.png"
                        }
                    }
                ]
                """;

        ArrayList<StoreAllPojo> storeAllPojoTestingArray = new ArrayList<>();

        StoreAllPojo firstTestingObjectOfStoreAllPojo = new StoreAllPojo();
        StoreAllPojo secondTestingObjectOfStoreAllPojo = new StoreAllPojo();

        firstTestingObjectOfStoreAllPojo.setId("store001");
        firstTestingObjectOfStoreAllPojo.setName("Game Store Alpha");
        firstTestingObjectOfStoreAllPojo.setActive(true);

        secondTestingObjectOfStoreAllPojo.setId("store002");
        secondTestingObjectOfStoreAllPojo.setName("Game Store Beta");
        secondTestingObjectOfStoreAllPojo.setActive(false);

        storeAllPojoTestingArray.add(firstTestingObjectOfStoreAllPojo);
        storeAllPojoTestingArray.add(secondTestingObjectOfStoreAllPojo);

        when(mockJsonMapper.mapArrayOfAllStoresToJava(json)).thenReturn(storeAllPojoTestingArray);

        List<StoreAllPojo> result = mockJsonMapper.mapArrayOfAllStoresToJava(json);

        assertEquals(2, storeAllPojoTestingArray.size());

        assertEquals("store001", firstTestingObjectOfStoreAllPojo.getId());
        assertEquals("Game Store Alpha", firstTestingObjectOfStoreAllPojo.getName());
        assertTrue(firstTestingObjectOfStoreAllPojo.isActive());

        assertEquals("store002", secondTestingObjectOfStoreAllPojo.getId());
        assertEquals("Game Store Beta", secondTestingObjectOfStoreAllPojo.getName());
        assertFalse(secondTestingObjectOfStoreAllPojo.isActive());
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForEmptyJson() {

        String json = "[]";

        ArrayList<StoreAllPojo> expectedResult = new ArrayList<>();
        when(mockJsonMapper.mapArrayOfAllStoresToJava(json)).thenReturn(expectedResult);

        List<StoreAllPojo> result = mockJsonMapper.mapArrayOfAllStoresToJava(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void mapToGameDealResponseTestForSuccessOfProperInput() {

        String json = """
                [
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
                ]
                """;


        GameDealResponse gameDealResponse = new GameDealResponse();


        GameInfo gameInfo = new GameInfo();
        gameInfo.setTitle("Game Title 1");
        gameInfo.setSteamAppID("12345");
        gameInfo.setThumb("https://example.com/game1/thumb.jpg");
        gameDealResponse.setInfo(gameInfo);

        CheapestPrice cheapestPriceEver = new CheapestPrice();
        cheapestPriceEver.setPrice("14.99");
        cheapestPriceEver.setDate(1596240000L); // Assuming date is a long
        gameDealResponse.setCheapestPriceEver(cheapestPriceEver);

        List<LookupDeal> deals = new ArrayList<>();
        LookupDeal deal1 = new LookupDeal();
        deal1.setStoreID("store1");
        deal1.setDealID("deal1");
        deal1.setPrice("19.99");
        deal1.setRetailPrice("29.99");
        deal1.setSavings("33.34");

        LookupDeal deal2 = new LookupDeal();
        deal2.setStoreID("store2");
        deal2.setDealID("deal2");
        deal2.setPrice("17.99");
        deal2.setRetailPrice("27.99");
        deal2.setSavings("35.73");

        deals.add(deal1);
        deals.add(deal2);

        gameDealResponse.setDeals(deals);


        when(mockJsonMapper.mapToGameDealResponse(json)).thenReturn(gameDealResponse);

        GameDealResponse result = mockJsonMapper.mapToGameDealResponse(json);

        assertEquals("Game Title 1", result.getInfo().getTitle());
        assertEquals("12345", result.getInfo().getSteamAppID());
        assertEquals("https://example.com/game1/thumb.jpg", result.getInfo().getThumb());

        assertEquals("14.99", result.getCheapestPriceEver().getPrice());
        assertEquals(1596240000L, result.getCheapestPriceEver().getDate());

        assertEquals(2, result.getDeals().size());

        assertEquals("store1", result.getDeals().getFirst().getStoreID());
        assertEquals("deal1", result.getDeals().getFirst().getDealID());
        assertEquals("19.99", result.getDeals().getFirst().getPrice());
        assertEquals("29.99", result.getDeals().getFirst().getRetailPrice());
        assertEquals("33.34", result.getDeals().getFirst().getSavings());

        assertEquals("store2", result.getDeals().get(1).getStoreID());
        assertEquals("deal2", result.getDeals().get(1).getDealID());
        assertEquals("17.99", result.getDeals().get(1).getPrice());
        assertEquals("27.99", result.getDeals().get(1).getRetailPrice());
        assertEquals("35.73", result.getDeals().get(1).getSavings());
    }

    @Test
    public void mapGameDealResponseToJavaTestForEmptyJson() {

        String json = "[]";

        GameDealResponse expectedResult = new GameDealResponse();
        when(mockJsonMapper.mapToGameDealResponse(json)).thenReturn(expectedResult);

        GameDealResponse result = mockJsonMapper.mapToGameDealResponse(json);

        assertEquals(expectedResult, result);
    }
}

