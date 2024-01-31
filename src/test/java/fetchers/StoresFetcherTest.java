package fetchers;

import dev.Roach.fetchers.StoresFetcher;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;

@ExtendWith(MockitoExtension.class)
public class StoresFetcherTest {

    private final HttpClient mockClient = Mockito.mock(HttpClient.class);
    private final StoresFetcher mockitoStoresFetcher = new StoresFetcher(mockClient);

//    @Test
//    public void getAllShopsCheckForCorrectResponse() throws IOException, InterruptedException {
//
//        String expectedResult = "Stores list";
//
//        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
//
//        when(mockResponse.body()).thenReturn("Stores list");
//        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
//                .thenReturn(mockResponse);
//
//        String result = mockitoStoresFetcher.getAllShops();
//
//        Assertions.assertEquals(expectedResult, result);
//
//    }



}
