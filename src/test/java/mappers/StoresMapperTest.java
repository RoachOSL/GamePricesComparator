package mappers;

import dev.Roach.datamodel.Store;
import dev.Roach.mappers.StoresMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoresMapperTest {

    private final StoresMapper storesMapper = new StoresMapper();

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

        List<Store> result = storesMapper.mapArrayOfAllStoresToJava(json);

        assertEquals(2, result.size());

        assertEquals("store001", result.get(0).getId());
        assertEquals("Game Store Alpha", result.get(0).getName());
        assertTrue(result.get(0).isActive());

        assertEquals("store002", result.get(1).getId());
        assertEquals("Game Store Beta", result.get(1).getName());
        assertFalse(result.get(1).isActive());
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForEmptyJson() {
        String json = "[]";
        List<Store> result = storesMapper.mapArrayOfAllStoresToJava(json);
        assertTrue(result.isEmpty());
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForMalformedJson() {
        String jsonMalformed = "{[}]";
        StoresMapper storesMapper = new StoresMapper();
        assertThrows(RuntimeException.class, () -> storesMapper.mapArrayOfAllStoresToJava(jsonMalformed));
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForJsonWithMissingFields() {
        String json = """
                [
                    {
                        "storeID": "store003",
                        "isActive": true
                    }
                ]
                """;

        StoresMapper storesMapper = new StoresMapper();
        List<Store> result = storesMapper.mapArrayOfAllStoresToJava(json);

        assertEquals(1, result.size());
        assertEquals("store003", result.get(0).getId());
        assertNull(result.get(0).getName());
        assertTrue(result.get(0).isActive());
    }

    @Test
    public void mapArrayOfAllStoresToJavaTestForAdditionalUnmappedFields() {
        String json = """
                [
                    {
                        "storeID": "store004",
                        "storeName": "Game Store Delta",
                        "isActive": true,
                        "extraField": "shouldBeIgnored"
                    }
                ]
                """;

        List<Store> result = storesMapper.mapArrayOfAllStoresToJava(json);

        assertEquals(1, result.size());
        assertEquals("store004", result.get(0).getId());
        assertEquals("Game Store Delta", result.get(0).getName());
        assertTrue(result.get(0).isActive());
    }
}
