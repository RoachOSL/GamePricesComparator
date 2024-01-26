package fetchers;

import dev.Roach.fetchers.StoresFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoresFetcherTest {

    private final HttpClient mockClient = Mockito.mock(HttpClient.class);
    private final StoresFetcher mockitoStoresFetcher = new StoresFetcher(mockClient);

    @Test
    public void getAllShopsCheckForCorrectResponse() throws IOException, InterruptedException {

        String expectedResult = "Stores list";

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("Stores list");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String result = mockitoStoresFetcher.getAllShops();

        Assertions.assertEquals(expectedResult, result);

    }



}
