package fetchers;

import dev.Roach.datamodel.GameLookUp;
import dev.Roach.fetchers.GameLookUpFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameLookUpFetcherTest {
    @Mock
    private HttpClient mockClient;

    @InjectMocks
    private GameLookUpFetcher gameLookUpFetcher;

    @BeforeEach
    public void setUp() {
        gameLookUpFetcher = new GameLookUpFetcher();
        gameLookUpFetcher.setClient(mockClient);
    }

    @Test
    public void getGameLookUpObjectUsingIDResponseForValidID() throws IOException, InterruptedException {
        String jsonResponse = """
                {
                  "info": {
                    "someInfoKey": "someInfoValue"
                  },
                  "cheapestPriceEver": {
                    "price": "10",
                    "date": "123456789"
                  },
                  "deals": [
                    {
                      "dealID": "1",
                      "price": "15",
                      "retailPrice": "20"
                    }
                  ]
                }""";

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(jsonResponse);

        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(mockResponse);

        GameLookUp actualResponse = gameLookUpFetcher.getGameLookUpObjectUsingID(5);

        assertNotNull(actualResponse);

        assertEquals("someInfoValue", actualResponse.getInfo().get("someInfoKey"));

        assertEquals("10", actualResponse.getCheapestPriceEver().get("price"));

        assertFalse(actualResponse.getDeals().isEmpty());

        Map<String, Object> firstDeal = actualResponse.getDeals().get(0);

        assertEquals("15", firstDeal.get("price"));

        assertEquals("1", firstDeal.get("dealID"));
    }

    @Test
    public void getGameLookUpObjectUsingIDForInvalidGameIdAndMalformedJSONResponse() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("");
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(mockResponse);

        GameLookUp result = gameLookUpFetcher.getGameLookUpObjectUsingID(-1);

        System.out.println(result);

        assertTrue(result.isEmpty());
        assertTrue(result.getInfo().isEmpty());
        assertTrue(result.getCheapestPriceEver().isEmpty());
        assertTrue(result.getDeals().isEmpty());
    }

    @Test
    public void getGameLookUpObjectUsingIDForInterruptionHandling() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenThrow(InterruptedException.class);

        GameLookUp result = gameLookUpFetcher.getGameLookUpObjectUsingID(5);

        assertTrue(result.isEmpty());
    }

    @Test
    public void getGameLookUpObjectUsingIDForNetworkErrorHandling() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenThrow(IOException.class);

        GameLookUp result = gameLookUpFetcher.getGameLookUpObjectUsingID(5);

        assertTrue(result.isEmpty());
    }
}
