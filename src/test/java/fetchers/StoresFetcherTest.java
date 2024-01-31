package fetchers;

import dev.Roach.datamodel.store.StoreAllPojo;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoresFetcherTest {

    private final HttpClient mockClient = Mockito.mock(HttpClient.class);
    private final StoresFetcher mockitoStoresFetcher = new StoresFetcher(mockClient);

    @Test
    public void getAllShopsCheckForCorrectResponse() throws IOException, InterruptedException {

        String expectedResult = "testID";

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[{\"storeID\":\"testID\"}]");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ArrayList<StoreAllPojo> result = mockitoStoresFetcher.getAllShops();

        Assertions.assertEquals(expectedResult, result.getFirst().getId());
    }

    @Test
    public void getAllShopsShouldReturnEmptyListOnEmptyResponse() throws IOException, InterruptedException {

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[]");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ArrayList<StoreAllPojo> result = mockitoStoresFetcher.getAllShops();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllShopsShouldHandleInvalidResponse() throws IOException, InterruptedException {

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("fafafa");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ArrayList<StoreAllPojo> result = mockitoStoresFetcher.getAllShops();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllShopsShouldHandleIOException() throws IOException, InterruptedException {

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException());

        ArrayList<StoreAllPojo> result = mockitoStoresFetcher.getAllShops();

        assertTrue(result.isEmpty());

    }

    @Test
    public void getAllShopsShouldHandleInterruptedException() throws IOException, InterruptedException {

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException());

        ArrayList<StoreAllPojo> result = mockitoStoresFetcher.getAllShops();

        assertTrue(result.isEmpty());

    }


}
