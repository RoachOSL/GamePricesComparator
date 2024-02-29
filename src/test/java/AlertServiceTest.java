import dev.Roach.AlertService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @Mock
    private HttpClient mockClient;
    @Mock
    private GamesFetcher mockGamesFetcher;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    public void setUp() {
        alertService = new AlertService();
        alertService.setHttpClient(mockClient);
    }

    @Test
    public void testCreateOrUpdateAlertShouldReturnCorrectInput() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("true");
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

        boolean isAlertCreatedOrUpdated = alertService.createOrUpdateAlert("test@example.com", 1, 9.99);

        assertTrue(isAlertCreatedOrUpdated);
    }

    @Test
    public void testGetAlertsForEmailShouldReturnCorrectResponse() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("Example link send");
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

        String alertsResponse = alertService.getAlertsForEmail("test@example.com");

        assertEquals("Example link send", alertsResponse);
    }

    @Test
    public void testCreateOrUpdateAlertWithGameTitleShouldReturnCorrectInput() throws IOException, InterruptedException {
        GamePojo mockGamePojo = new GamePojo();
        mockGamePojo.setTitle("Test title");

        when(mockGamesFetcher.getGameContainingKeyword(anyString())).thenReturn(List.of(mockGamePojo));

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("true");
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

        alertService.setGamesFetcher(mockGamesFetcher);

        boolean isAlertUpdatedForGameTitle = alertService.createOrUpdateAlertWithGameTitle("Test title", "test@example.com", 4.45);

        assertTrue(isAlertUpdatedForGameTitle);
    }

    @Test
    public void testDeleteAlertShouldReturnTrueOnSuccess() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("true");
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);


        boolean isAlertDeleted = alertService.deleteAlert("test@example.com", 1);

        assertTrue(isAlertDeleted);
    }

    @Test
    public void testCreateOrUpdateAlertWithGameTitleShouldReturnFalseWhenGameNotFound() throws IOException, InterruptedException {
        when(mockGamesFetcher.getGameContainingKeyword(anyString())).thenReturn(List.of());

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("false");
        when(mockClient.send(any(HttpRequest.class),
                any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockResponse);

        alertService.setGamesFetcher(mockGamesFetcher);

        boolean isAlertNotCreatedForMissingGame = alertService.createOrUpdateAlertWithGameTitle("Non existing game", "test@example.com", 4.45);

        assertFalse(isAlertNotCreatedForMissingGame);
    }

    @Test
    public void testCreateOrUpdateAlertWithNullGameTitleThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            alertService.createOrUpdateAlertWithGameTitle(null, "test@example.com", 4.45);
        });

        assertEquals("Game title cannot be null or empty.", thrown.getMessage());
    }

    @Test
    public void testCreateOrUpdateAlertWithNullEmailThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            alertService.createOrUpdateAlertWithGameTitle("Some game", null, 4.45);
        });

        assertEquals("Email cannot be null or empty.", thrown.getMessage());
    }
}

