package fetchers;

import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.fetchers.GamesFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GamesFetcherTest {

    @Mock
    private HttpClient mockClient;

    @InjectMocks
    private GamesFetcher gamesFetcher;

    @BeforeEach
    public void setUp() {
        gamesFetcher = new GamesFetcher();
        gamesFetcher.setClient(mockClient);
    }

    @Test
    public void getGameContainingKeywordResponseForValidKeyword() throws IOException, InterruptedException {

        String keyword = "gameTest";
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[{\"steamAppID\":\"testID\"}]");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        List<GamePojo> result = gamesFetcher.getGameContainingKeyword(keyword);

        assertEquals("testID", result.getFirst().getSteamID());
    }

    @Test
    public void getGameContainsNullAsKeyword() {

        try {
            gamesFetcher.getGameContainingKeyword(null);
        } catch (NullPointerException e) {
            assertEquals("Keyword can't be a null", e.getMessage());
        }
    }

    @Test
    public void getGameUsingIDResponseForValidKeyword() throws IOException, InterruptedException {

        int testID = 52671;

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("Test for valid ID");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String testResult = gamesFetcher.getGameUsingID(testID);

        assertEquals("Test for valid ID", testResult);
    }

    @Test
    public void getGameUsingIDThatDoesNotExist() throws IOException, InterruptedException {

        int id = 24380221;

        HttpResponse<String> mockitoResponse = Mockito.mock(HttpResponse.class);

        when(mockitoResponse.body()).thenReturn("[]");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockitoResponse);

        String result = gamesFetcher.getGameUsingID(id);

        assertEquals("Wrong ID, game doesn't exist", result);
    }

}