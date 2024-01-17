import dev.Roach.DealsFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class DealsFetcherTest {

    private final HttpClient mockClient = Mockito.mock(HttpClient.class);
    private final DealsFetcher mockitoDealsFetcher = new DealsFetcher(mockClient);

    @Test
    public void getAllDealsShouldHandleInterruptedException() throws IOException, InterruptedException {

        Mockito.when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).thenThrow(InterruptedException.class);

        List<String> allDeals = mockitoDealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getDealUsingIDShouldReturnExpectedResponseWhenIdIsNull() throws IOException, InterruptedException {

        DealsFetcher dealsFetcher = new DealsFetcher(null);

        String actualResponse = dealsFetcher.getDealUsingID(null);

        Assertions.assertEquals("NULL", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnValidResponseForValidId() throws IOException, InterruptedException {

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        String expectedResponse = "Information about deal";
        String testId = "44677";

        Mockito.when(mockResponse.body()).thenReturn(expectedResponse);

        Mockito.when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        String actualResponse = mockitoDealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
