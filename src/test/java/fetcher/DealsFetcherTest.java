package fetcher;

import dev.Roach.fetcher.DealsFetcher;
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
import java.util.List;
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

        Mockito.when(firstMockResponse.body()).thenReturn("First page content");
        Mockito.when(secondMockResponse.body()).thenReturn("Second page content");
        Mockito.when(thirdMockResponse.body()).thenReturn("Third page content");

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(initialMockResponse)
                .thenReturn(firstMockResponse)
                .thenReturn(secondMockResponse)
                .thenReturn(thirdMockResponse);

        List<String> allDeals = mockitoDealsFetcher.getAllDeals();

        Assertions.assertEquals(3, allDeals.size());
        Assertions.assertEquals("First page content", allDeals.get(0));
        Assertions.assertEquals("Second page content", allDeals.get(1));
        Assertions.assertEquals("Third page content", allDeals.get(2));
    }

    @Test
    public void getAllDealsShouldHandleInterruptedException() throws IOException, InterruptedException {

        Mockito.when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).thenThrow(InterruptedException.class);

        List<String> allDeals = mockitoDealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getAllDealsShouldHandleIOException() throws IOException, InterruptedException {

        Mockito.when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(IOException.class);

        List<String> allDeals = mockitoDealsFetcher.getAllDeals();

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
