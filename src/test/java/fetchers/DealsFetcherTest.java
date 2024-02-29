package fetchers;

import dev.Roach.datamodel.deal.DealAllListPojo;
import dev.Roach.fetchers.DealsFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DealsFetcherTest {

    @Mock
    private HttpClient mockClient;

    @InjectMocks
    private DealsFetcher dealsFetcher;

    @BeforeEach
    public void setUp() {
        dealsFetcher = new DealsFetcher();
        dealsFetcher.setClient(mockClient);
    }

    @Test
    public void getAllDealsReturnCorrectPages() throws IOException, InterruptedException {
        HttpResponse<String> initialMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> firstMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> secondMockResponse = Mockito.mock(HttpResponse.class);
        HttpResponse<String> thirdMockResponse = Mockito.mock(HttpResponse.class);

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        when(mockHeaders.firstValue("X-Total-Page-Count"))
                .thenReturn(Optional.of("3"));

        when(initialMockResponse.headers()).thenReturn(mockHeaders);

        when(firstMockResponse.body()).thenReturn("[{\"title\":\"Deal 1\"}]");
        when(secondMockResponse.body()).thenReturn("[{\"metacriticLink\":\"meta\"}]");
        when(thirdMockResponse.body()).thenReturn("[{\"savings\":\"30.3\"}]");

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(initialMockResponse)
                .thenReturn(firstMockResponse)
                .thenReturn(secondMockResponse)
                .thenReturn(thirdMockResponse);

        List<DealAllListPojo> allDeals = dealsFetcher.getAllDeals();

        Assertions.assertEquals(3, allDeals.size());

        Assertions.assertEquals("Deal 1", allDeals.get(0).getDeals().getFirst().getTitle());
        Assertions.assertEquals("meta", allDeals.get(1).getDeals().getFirst().getMetacriticLink());
        Assertions.assertEquals(30.3, allDeals.get(2).getDeals().getFirst().getSavings());
    }

    @Test
    public void getAllDealsShouldHandleInterruptedException() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))).thenThrow(InterruptedException.class);

        List<DealAllListPojo> allDeals = dealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getAllDealsShouldHandleIOException() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(IOException.class);

        List<DealAllListPojo> allDeals = dealsFetcher.getAllDeals();

        Assertions.assertTrue(allDeals.isEmpty());
    }

    @Test
    public void getDealUsingIDShouldReturnEmptyForInvalidId() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[]");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String testId = "invalidID";
        String actualResponse = dealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals("[]", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnExpectedResponseWhenIdIsNull() {
        String actualResponse = dealsFetcher.getDealUsingID(null);

        Assertions.assertEquals("NULL", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnValidResponseForValidId() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        String expectedResponse = "Information about deal";
        String testId = "44677";

        when(mockResponse.body()).thenReturn(expectedResponse);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String actualResponse = dealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}