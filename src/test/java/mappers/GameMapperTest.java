package mappers;

import dev.Roach.datamodel.Game;
import dev.Roach.mappers.GameMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameMapperTest {

    private final GameMapper gameMapper = new GameMapper();

    @Test
    public void mapArrayOfGameToJavaTestForSuccessOfProperInput() {
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

        List<Game> gameTestingArray = gameMapper.mapArrayOfGamePojoToJava(json);

        assertEquals(1, gameTestingArray.size());
        assertEquals(12345, gameTestingArray.get(0).getGameID());
        assertEquals("67890", gameTestingArray.get(0).getSteamID());
        assertEquals("Game Title Internal", gameTestingArray.get(0).getTitle());
        assertEquals(19.99, gameTestingArray.get(0).getCheapestPrice(), 0.001);
    }

    @Test
    public void mapArrayOfGameToJavaTestForEmptyJson() {
        String json = "[]";

        List<Game> result = gameMapper.mapArrayOfGamePojoToJava(json);

        assertTrue(result.isEmpty());
    }

    @Test
    public void mapArrayOfGameToJavaTestForRuntimeException() {
        String json = "{[}]";

        assertThrows(RuntimeException.class, () -> gameMapper.mapArrayOfGamePojoToJava(json));
    }

    @Test
    public void mapArrayOfGameToJavaTestForPartiallyPopulatedJson() {
        String json = """
                [
                    {
                        "gameID": 12345,
                        "cheapest": 19.99
                    }
                ]
                """;

        List<Game> result = gameMapper.mapArrayOfGamePojoToJava(json);
        assertEquals(1, result.size());
        assertEquals(12345, result.get(0).getGameID());
        assertEquals(19.99, result.get(0).getCheapestPrice(), 0.001);
        assertNull(result.get(0).getSteamID());
    }
}
