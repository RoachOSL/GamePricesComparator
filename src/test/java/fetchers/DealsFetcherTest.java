package fetchers;

import dev.Roach.datamodel.deal.DealAllListPojo;
import dev.Roach.fetchers.DealsFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class DealsFetcherTest {

    private final HttpClient mockClient = Mockito.mock(HttpClient.class);
    private final DealsFetcher mockitoDealsFetcher = new DealsFetcher(mockClient);

    @Test
    public void getAllDealsReturnCorrectPages() throws IOException, InterruptedException {

        HttpResponse<String> initialMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> firstMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> secondMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> thirdMockResponse = Mockito.mock(HttpResponse.class);

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        Mockito.when(mockHeaders.firstValue("X-Total-Page-Count"))
                .thenReturn(Optional.of("3"));

        Mockito.when(initialMockResponse.headers()).thenReturn(mockHeaders);

        Mockito.when(firstMockResponse.body()).thenReturn("[{\"title\":\"Deal 1\"}]");
        Mockito.when(secondMockResponse.body()).thenReturn("[{\"metacriticLink\":\"meta\"}]");
        Mockito.when(thirdMockResponse.body()).thenReturn("[{\"savings\":\"30.3\"}]");

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(initialMockResponse)
                .thenReturn(firstMockResponse)
                .thenReturn(secondMockResponse)
                .thenReturn(thirdMockResponse);

        DealsFetcher dealsFetcher = new DealsFetcher(mockClient);
        ArrayList<DealAllListPojo> allDeals = dealsFetcher.getAllDeals();

        Assertions.assertEquals(3, allDeals.size());

        Assertions.assertEquals("Deal 1", allDeals.get(0).getDeals().getFirst().getTitle());
        Assertions.assertEquals("meta", allDeals.get(1).getDeals().getFirst().getMetacriticLink());
        Assertions.assertEquals(30.3, allDeals.get(2).getDeals().getFirst().getSavings());
    }

    @Test
    public void getAllDealsShouldHandleInterruptedException() throws IOException, InterruptedException {

        Mockito.when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).thenThrow(InterruptedException.class);

        ArrayList<DealAllListPojo> allDeals = mockitoDealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getAllDealsShouldHandleIOException() throws IOException, InterruptedException {

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(IOException.class);

        ArrayList<DealAllListPojo> allDeals = mockitoDealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getDealUsingIDShouldReturnEmptyForInvalidId() throws IOException, InterruptedException {

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(mockResponse.body()).thenReturn("[]");
        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String testId = "invalidID";
        String actualResponse = mockitoDealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals("[]", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnExpectedResponseWhenIdIsNull() {

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

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String actualResponse = mockitoDealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
