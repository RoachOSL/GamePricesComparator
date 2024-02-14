import dev.Roach.AlertService;
import dev.Roach.GameLookup;
import dev.Roach.Menu;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.CheapestPrice;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.gameLookup.GameInfo;
import dev.Roach.datamodel.gameLookup.LookupDeal;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuTest {

    @Mock
    private Scanner scanner;

    @Mock
    private StoresFetcher storesFetcher;
    @Mock
    private GameLookup gameLookup;
    @Mock
    private GamesFetcher gamesFetcher;
    @Mock
    private AlertService alertService;

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu(scanner, storesFetcher, gameLookup, gamesFetcher, alertService);
    }

    @Test
    void whenOptionOneThenSearchForDealsByTitleShouldInvokeOnlyOnce() {

        GameDealResponse response = new GameDealResponse();
        GameInfo gameInfo = new GameInfo();
        gameInfo.setTitle("Fake Title");
        response.setInfo(gameInfo);
        CheapestPrice cheapestPrice = new CheapestPrice();
        cheapestPrice.setPrice("5");
        response.setCheapestPriceEver(cheapestPrice);
        List<LookupDeal> deals = new ArrayList<>();
        LookupDeal lookupDeal = new LookupDeal();
        lookupDeal.setPrice("15");
        deals.add(lookupDeal);
        response.setDeals(deals);

        when(scanner.nextLine()).thenReturn("1", "E");

        when(gameLookup.giveTitleToGetListOFDealsWithStores(anyString())).thenReturn(response);

        menu.startTheProgram();

        verify(gameLookup, times(1)).giveTitleToGetListOFDealsWithStores(anyString());
    }

    @Test
    void whenOptionTwoThenSearchForDealsByKeywordShouldInvokeOnlyOnce() {

        when(scanner.nextLine()).thenReturn("2", "Keyword", "E");

        List<GamePojo> gamePojos = new ArrayList<>();
        GamePojo gamePojo = new GamePojo();
        gamePojo.setTitle("Test Title");
        gamePojos.add(gamePojo);

        when(gamesFetcher.getGameContainingKeyword(anyString())).thenReturn(gamePojos);

        menu.startTheProgram();

        verify(gamesFetcher, times(1)).getGameContainingKeyword(anyString());
    }

    @Test
    void whenOptionThreeThenCreateOrUpdateAlertShouldInvokeOnlyOnce() {

        when(scanner.nextLine()).thenReturn("3", "Test Game Title", "5", "test@gmail.com", "E");

        when(alertService.createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble())).thenReturn(true);

        menu.startTheProgram();

        verify(alertService, times(1)).createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble());
    }

    @Test
    void whenOptionFourThenManageAlertsByEmailShouldInvokeOnlyOnce() {

        when(scanner.nextLine()).thenReturn("4", "test@gmail.com", "E");

        String response = "Test response";

        when(alertService.getAlertsForEmail(anyString())).thenReturn(response);

        menu.startTheProgram();

        verify(alertService, times(1)).getAlertsForEmail(anyString());
    }

    @Test
    void whenOptionFiveShouldQuitProgram() {

        when(scanner.nextLine()).thenReturn("5");

        menu.startTheProgram();

        Assertions.assertFalse(menu.isRunning());
    }


}