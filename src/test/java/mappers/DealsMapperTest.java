package mappers;

import dev.Roach.datamodel.Deal;
import dev.Roach.mappers.DealsMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DealsMapperTest {

    private final DealsMapper dealsMapper = new DealsMapper();

    @Test
    public void mapDealToJavaTestForValidJson() {
        String json = """
                {
                    "storeID": "store5678",
                    "dealID": "deal1234",
                    "salePrice": "29.99",
                    "normalPrice": "59.99",
                    "savings": 50.00
                }
                """;

        Deal result = dealsMapper.mapDealToJava(json);

        assertEquals("deal1234", result.getDealID());
        assertEquals("store5678", result.getStoreID());
        assertEquals("29.99", result.getPrice());
        assertEquals("59.99", result.getRetailPrice());
        assertEquals(50.00, result.getSavings(), 0.001);
    }

    @Test
    public void mapDealToJavaTestForMalformedJson() {
        String json = "{]";

        assertThrows(RuntimeException.class, () -> dealsMapper.mapDealToJava(json));
    }

    @Test
    public void mapArrayOfDealsToJavaTestForSuccessOfProperInput() {
        String json = """
                [
                    {
                        "storeID": "store5678",
                        "dealID": "deal1234",
                        "salePrice": "29.99",
                        "normalPrice": "59.99",
                        "savings": 50.00
                    }
                ]
                """;

        List<Deal> result = dealsMapper.mapArrayOfDealsToJava(json);

        assertEquals(1, result.size());
        assertEquals("deal1234", result.get(0).getDealID());
        assertEquals("store5678", result.get(0).getStoreID());
        assertEquals(50.00, result.get(0).getSavings(), 0.001);
        assertEquals("29.99", result.get(0).getPrice());
        assertEquals("59.99", result.get(0).getRetailPrice());
    }

    @Test
    public void mapArrayOfDealsToJavaTestForEmptyJson() {
        String json = "[]";

        List<Deal> result = dealsMapper.mapArrayOfDealsToJava(json);

        assertTrue(result.isEmpty());
    }


}
