package services;

import dev.Roach.datamodel.Game;
import dev.Roach.datamodel.GameLookUp;
import dev.Roach.fetchers.GameLookUpFetcher;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.services.GameLookUpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameLookUpServiceTest {

    @Mock
    private GamesFetcher mockGamesFetcher;

    @Mock
    private GameLookUpFetcher mockGameLookUpFetcher;

    @InjectMocks
    private GameLookUpService gameLookUpService;

    @BeforeEach
    public void setUp() {
        gameLookUpService = new GameLookUpService();
        gameLookUpService.setGamesFetcher(mockGamesFetcher);
        gameLookUpService.setGameLookUpFetcher(mockGameLookUpFetcher);
    }

    @Test
    public void giveTitleToGetListOFDealsWithStoresWithValidTitle() {
        String keyword = "legobatman";
        Game mockGame = new Game("LEGO Batman", 12, "steam123", 19.99);

        when(mockGamesFetcher.getGameContainingKeyword(keyword.toUpperCase().replaceAll("\\s", "")))
                .thenReturn(Collections.singletonList(mockGame));

        GameLookUp mockGameLookUp = new GameLookUp();
        Map<String, Object> info = new HashMap<>();
        info.put("title", "LEGO Batman");
        mockGameLookUp.setInfo(info);

        when(mockGameLookUpFetcher.getGameLookUpObjectUsingID(12)).thenReturn(mockGameLookUp);

        GameLookUp result = gameLookUpService.giveTitleToGetListOfDealsWithStores(keyword);

        assertEquals("LEGO Batman", result.getInfo().get("title"));
    }

    @Test
    public void giveTitleToGetListOFDealsWithStoresWithInvalidTitle() {
        String keyword = "InvalidTitle";
        when(mockGamesFetcher.getGameContainingKeyword(keyword.toUpperCase().replaceAll("\\s", ""))).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookUpService.giveTitleToGetListOfDealsWithStores(keyword);
        });
    }

    @Test
    public void giveTitleToGetListOFDealsWithStoresWithNullTitle() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookUpService.giveTitleToGetListOfDealsWithStores(null);
        });
    }

    @Test
    public void giveTitleToGetListOFDealsWithStoresWithEmptyTitle() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            gameLookUpService.giveTitleToGetListOfDealsWithStores("");
        });
    }



}