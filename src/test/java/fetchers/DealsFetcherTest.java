package fetchers;

import dev.Roach.fetchers.DealsFetcher;
import org.junit.jupiter.api.Assertions;
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
    public void getDealUsingIDShouldReturnEmptyForInvalidID() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[]");

        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(mockResponse);


        String testId = "invalidID";
        String actualResponse = dealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals("[]", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnExpectedResponseWhenIdIsNull() {
        String actualResponse = dealsFetcher.getDealUsingID(null);

        Assertions.assertEquals("", actualResponse);
    }

    @Test
    public void getDealUsingIDShouldReturnValidResponseForValidID() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        String expectedResponse = "Information about deal";
        String testId = "44677";

        when(mockResponse.body()).thenReturn(expectedResponse);

        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(mockResponse);

        String actualResponse = dealsFetcher.getDealUsingID(testId);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}