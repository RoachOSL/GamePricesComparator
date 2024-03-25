//import dev.Roach.GameLookup;
//import dev.Roach.datamodel.GameLookUp;
//import dev.Roach.fetchers.GamesFetcher;
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
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class GameLookupTest {
//
//    @Mock
//    private GamesFetcher mockGamesFetcher;
//
//    @InjectMocks
//    private GameLookup gameLookup;
//
//    @BeforeEach
//    public void setUp() {
//        gameLookup = new GameLookup();
//        gameLookup.setGamesFetcher(mockGamesFetcher);
//    }
//
//    @Test
//    public void testGiveTitleToGetListOFDealsWithStoresWithValidTitle() {
//        String keyword = "legobatman";
//        String transformedGameTitle = keyword.toUpperCase().replaceAll("\\s", "");
//
//        GamePojo testGamePojo = new GamePojo();
//        testGamePojo.setTitle(transformedGameTitle);
//
//        List<GamePojo> mockGamePojoList = new ArrayList<>();
//        mockGamePojoList.add(testGamePojo);
//
//        when(mockGamesFetcher.getGameContainingKeyword(transformedGameTitle)).thenReturn(mockGamePojoList);
//
//        GameLookUp mockResponse = new GameLookUp();
//
//        GameInfo mockInfo = new GameInfo();
//        mockInfo.setTitle("LEGO Batman");
//        mockResponse.setInfo(mockInfo);
//        when(mockGamesFetcher.getGameDealObjectUsingID(anyInt())).thenReturn(mockResponse);
//
//        GameLookUp result = gameLookup.giveTitleToGetListOFDealsWithStores(transformedGameTitle);
//
//        assertEquals("LEGO Batman", result.getInfo().getTitle());
//    }
//
//    @Test
//    public void testGiveTitleToGetListOFDealsWithStoresWithInvalidTitle() {
//        String keyword = "InvalidTitle";
//        when(mockGamesFetcher.getGameContainingKeyword(keyword.toUpperCase().replaceAll("\\s", ""))).thenReturn(new ArrayList<>());
//
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            gameLookup.giveTitleToGetListOFDealsWithStores(keyword);
//        });
//    }
//
//    @Test
//    public void testWithNullTitle() {
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            gameLookup.giveTitleToGetListOFDealsWithStores(null);
//        });
//    }
//
//    @Test
//    public void testWithEmptyTitle() {
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            gameLookup.giveTitleToGetListOFDealsWithStores("");
//        });
//    }
//
//}