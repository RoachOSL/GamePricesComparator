//import dev.Roach.services.AlertService;
//import dev.Roach.GameLookup;
//import dev.Roach.ui.Menu;
//import dev.Roach.datamodel.GameLookUp;
//import dev.Roach.fetchers.GamesFetcher;
//import dev.Roach.fetchers.StoresFetcher;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MenuTest {
//
//    @Mock
//    private Scanner scanner;
//
//    @Mock
//    private StoresFetcher storesFetcher;
//    @Mock
//    private GameLookup gameLookup;
//    @Mock
//    private GamesFetcher gamesFetcher;
//    @Mock
//    private AlertService alertService;
//
//    @InjectMocks
//    private Menu menu;
//
//    @BeforeEach
//    public void setUp() {
//        menu = new Menu(scanner, storesFetcher, gameLookup, gamesFetcher, alertService);
//    }
//
//    @Test
//    public void whenOptionOneThenSearchForDealsByTitleShouldInvokeOnlyOnce() {
//        GameLookUp response = new GameLookUp();
//        GameInfo gameInfo = new GameInfo();
//        gameInfo.setTitle("Fake Title");
//        response.setInfo(gameInfo);
//        CheapestPrice cheapestPrice = new CheapestPrice();
//        cheapestPrice.setPrice("5");
//        response.setCheapestPriceEver(cheapestPrice);
//        List<LookupDeal> deals = new ArrayList<>();
//        LookupDeal lookupDeal = new LookupDeal();
//        lookupDeal.setPrice("15");
//        deals.add(lookupDeal);
//        response.setDeals(deals);
//
//        when(scanner.nextLine()).thenReturn("1", "E");
//
//        when(gameLookup.giveTitleToGetListOFDealsWithStores(anyString())).thenReturn(response);
//
//        menu.startTheProgram();
//
//        verify(gameLookup, times(1)).giveTitleToGetListOFDealsWithStores(anyString());
//    }
//
//    @Test
//    public void whenOptionTwoThenSearchForDealsByKeywordShouldInvokeOnlyOnce() {
//        when(scanner.nextLine()).thenReturn("2", "Keyword", "E");
//
//        List<GamePojo> gamePojos = new ArrayList<>();
//        GamePojo gamePojo = new GamePojo();
//        gamePojo.setTitle("Test Title");
//        gamePojos.add(gamePojo);
//
//        when(gamesFetcher.getGameContainingKeyword(anyString())).thenReturn(gamePojos);
//
//        menu.startTheProgram();
//
//        verify(gamesFetcher, times(1)).getGameContainingKeyword(anyString());
//    }
//
//    @Test
//    public void whenOptionThreeThenCreateOrUpdateAlertShouldInvokeOnlyOnce() {
//        GameLookUp mockResponse = mock(GameLookUp.class);
//
//        when(mockResponse.isEmpty()).thenReturn(false);
//        when(gameLookup.giveTitleToGetListOFDealsWithStores(anyString())).thenReturn(mockResponse);
//
//        when(scanner.nextLine()).thenReturn("3", "Test Game Title", "5", "test@gmail.com", "E");
//
//        when(alertService.createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble())).thenReturn(true);
//
//        menu.startTheProgram();
//
//        verify(alertService, times(1)).createOrUpdateAlertWithGameTitle(anyString(), anyString(), anyDouble());
//    }
//
//    @Test
//    public void whenOptionFourThenManageAlertsByEmailShouldInvokeOnlyOnce() {
//        when(scanner.nextLine()).thenReturn("4", "test@gmail.com", "E");
//
//        String response = "Test response";
//
//        when(alertService.getAlertsForEmail(anyString())).thenReturn(response);
//
//        menu.startTheProgram();
//
//        verify(alertService, times(1)).getAlertsForEmail(anyString());
//    }
//
//    @Test
//    public void whenOptionFiveShouldQuitProgram() {
//        when(scanner.nextLine()).thenReturn("5");
//
//        menu.startTheProgram();
//
//        Assertions.assertFalse(menu.isRunning());
//    }
//
//}