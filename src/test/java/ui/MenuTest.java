import dev.Roach.datamodel.Game;
import dev.Roach.datamodel.GameLookUp;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;
import dev.Roach.services.AlertService;
import dev.Roach.services.GameLookUpService;
import dev.Roach.ui.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuTest {

    @Mock
    private Scanner scanner;

    @Mock
    private StoresFetcher storesFetcher;
    @Mock
    private GameLookUpService gameLookUpService;
    @Mock
    private GamesFetcher gamesFetcher;
    @Mock
    private AlertService alertService;

    @InjectMocks
    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu(scanner, storesFetcher, gameLookUpService, gamesFetcher, alertService);
    }

    @Test
    public void whenOptionOneThenSearchForDealsByTitleShouldInvokeOnlyOnce() {
        GameLookUp response = new GameLookUp();
        Map<String, Object> info = new HashMap<>();
        info.put("title", "Fake Title");
        response.setInfo(info);

        Map<String, Object> cheapestPriceEver = new HashMap<>();
        cheapestPriceEver.put("price", "5");
        response.setCheapestPriceEver(cheapestPriceEver);

        List<Map<String, Object>> deals = new ArrayList<>();
        Map<String, Object> deal = new HashMap<>();
        deal.put("price", "15");
        deals.add(deal);
        response.setDeals(deals);

        when(scanner.nextLine()).thenReturn("1", "Fake Title", "E");

        when(gameLookUpService.giveTitleToGetListOfDealsWithStores("Fake Title")).thenReturn(response);

        menu.startTheProgram();

        verify(gameLookUpService, times(1)).giveTitleToGetListOfDealsWithStores("Fake Title");
    }

    @Test
    public void whenOptionTwoThenSearchForDealsByKeywordShouldInvokeOnlyOnce() {
        when(scanner.nextLine()).thenReturn("2", "Keyword", "E");

        List<Game> games = new ArrayList<>();
        games.add(new Game("Test Title", 123, "steam123", 19.99));

        when(gamesFetcher.getGameContainingKeyword(anyString())).thenReturn(games);

        menu.startTheProgram();

        verify(gamesFetcher, times(1)).getGameContainingKeyword(anyString());
    }

    @Test
    public void whenOptionThreeThenCreateOrUpdateAlertShouldInvokeOnlyOnce() {
        GameLookUp mockResponse = mock(GameLookUp.class);

        when(mockResponse.isEmpty()).thenReturn(false);
        when(gameLookUpService.giveTitleToGetListOfDealsWithStores(anyString())).thenReturn(mockResponse);

        when(scanner.nextLine()).thenReturn("3", "Test Game Title", "5", "test@gmail.com", "E");

        when(alertService.createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble())).thenReturn(true);

        menu.startTheProgram();

        verify(alertService, times(1)).createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble());
    }

    @Test
    public void whenOptionFourThenManageAlertsByEmailShouldInvokeOnlyOnce() {
        when(scanner.nextLine()).thenReturn("4", "test@gmail.com", "E");

        String response = "Test response";

        when(alertService.getAlertsForEmail(anyString())).thenReturn(response);

        menu.startTheProgram();

        verify(alertService, times(1)).getAlertsForEmail(anyString());
    }

    @Test
    public void whenOptionFiveShouldQuitProgram() {
        when(scanner.nextLine()).thenReturn("5");

        menu.startTheProgram();

        Assertions.assertFalse(menu.isRunning());
    }

}