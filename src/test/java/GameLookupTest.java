import dev.Roach.GameLookup;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.gameLookup.GameInfo;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.gameLookup.GameInfo;
import dev.Roach.fetchers.GamesFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameLookupTest {

    @Mock
    private GamesFetcher mockGamesFetcher;

    private GameLookup gameLookup;

    @BeforeEach
    void setUp() {
        gameLookup = new GameLookup();
        gameLookup.setGamesFetcher(mockGamesFetcher);
    }

    @Test
    public void testGiveTitleToGetListOFDealsWithStoresWithValidTitle() {

        String keyword = "legobatman";
        String transformedGameTitle = keyword.toUpperCase().replaceAll("\\s", "");

        GamePojo testGamePojo = new GamePojo();
        testGamePojo.setTitle(transformedGameTitle);

        List<GamePojo> mockGamePojoList = new ArrayList<>();
        mockGamePojoList.add(testGamePojo);

        when(mockGamesFetcher.getGameContainingKeyword(transformedGameTitle)).thenReturn(mockGamePojoList);

        GameDealResponse mockResponse = new GameDealResponse();

        GameInfo mockInfo = new GameInfo();
        mockInfo.setTitle("LEGO Batman");
        mockResponse.setInfo(mockInfo);
        when(mockGamesFetcher.getGameDealObjectUsingID(anyInt())).thenReturn(mockResponse);

        GameDealResponse result = gameLookup.giveTitleToGetListOFDealsWithStores(transformedGameTitle);

        assertEquals("LEGO Batman", result.getInfo().getTitle());
    }

    @Test
    public void testGiveTitleToGetListOFDealsWithStoresWithInvalidTitle() {

        String keyword = "InvalidTitle";
        when(mockGamesFetcher.getGameContainingKeyword(keyword.toUpperCase().replaceAll("\\s", ""))).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookup.giveTitleToGetListOFDealsWithStores(keyword);
        });
    }

    @Test
    public void testWithNullTitle() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookup.giveTitleToGetListOFDealsWithStores(null);
        });
    }

    @Test
    public void testWithEmptyTitle() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookup.giveTitleToGetListOFDealsWithStores("");
        });
    }

}