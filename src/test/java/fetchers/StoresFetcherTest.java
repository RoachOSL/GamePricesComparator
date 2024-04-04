package fetchers;

import dev.Roach.datamodel.Store;
import dev.Roach.fetchers.StoresFetcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoresFetcherTest {

    private static final String FILE_PATH_TO_ALL_DEALS = "src/main/resources/ShopList.txt";

    @Mock
    private HttpClient mockClient;

    @InjectMocks
    private StoresFetcher storesFetcher;

    @BeforeEach
    public void setUp() {
        storesFetcher = new StoresFetcher();
        storesFetcher.setClient(mockClient);
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH_TO_ALL_DEALS));
    }

    @Test
    public void getAllShopsCheckForCorrectResponse() throws IOException, InterruptedException {
        String expectedResult = "testID";

        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[{\"storeID\":\"testID\"}]");

        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);

        List<Store> result = storesFetcher.getAllShopsAndWriteItToTheFile();

        assertEquals(expectedResult, result.get(0).getId());
    }

    @Test
    public void getAllShopsShouldReturnEmptyListOnEmptyResponse() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("[]");

        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);

        List<Store> result = storesFetcher.getAllShopsAndWriteItToTheFile();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllShopsForInvalidResponse() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn("fafafa");
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);

        List<Store> result = storesFetcher.getAllShopsAndWriteItToTheFile();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllShopsForIOException() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenThrow(new IOException());

        List<Store> result = storesFetcher.getAllShopsAndWriteItToTheFile();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllShopsForInterruptedException() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenThrow(new InterruptedException());

        List<Store> result = storesFetcher.getAllShopsAndWriteItToTheFile();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchStoreDataForCorrectDataSuccess() throws IOException, InterruptedException {
        String expectedResponseBody = "expected response";
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(expectedResponseBody);
        when(mockClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
        String result = storesFetcher.fetchStoreData();

        assertEquals(expectedResponseBody, result);
    }

    @Test
    public void testFetchStoreDataForIOException() throws IOException, InterruptedException {
        when(mockClient.send(any(HttpRequest.class), any())).thenThrow(IOException.class);

        String result = storesFetcher.fetchStoreData();

        assertEquals("", result);
    }

    @Test
    public void writeDataToFileShouldCorrectlyWriteData() throws IOException {
        String dataToWrite = "Test Store Data";

        storesFetcher.writeDataToFile(dataToWrite);

        String fileContent = Files.readString(Path.of(FILE_PATH_TO_ALL_DEALS));
        assertEquals(dataToWrite, fileContent);
    }


    @Test
    public void readAllShopsFromFileShouldReadWhenFileIsRecentAndWorksProperly() throws IOException {
        String jsonToWrite = "[{\"storeID\":\"1\",\"storeName\":\"Test Store\",\"isActive\":true}]";
        Path filePath = Paths.get(FILE_PATH_TO_ALL_DEALS);
        Files.write(filePath, jsonToWrite.getBytes());

        boolean modified = new File(FILE_PATH_TO_ALL_DEALS).setLastModified(System.currentTimeMillis());
        Assertions.assertTrue(modified);

        List<Store> stores = storesFetcher.readAllShopsFromFile();

        Assertions.assertNotNull(stores);
        Assertions.assertFalse(stores.isEmpty());

        Store expectedStore = stores.get(0);
        assertEquals("1", expectedStore.getId());
        assertEquals("Test Store", expectedStore.getName());
        Assertions.assertTrue(expectedStore.isActive());
    }

    @Test
    public void readAllShopsFromFileShouldNotReadWhenFileIsNotRecent() throws IOException {
        Path filePath = Paths.get(FILE_PATH_TO_ALL_DEALS);
        Files.createFile(filePath);

        boolean modified = new File(FILE_PATH_TO_ALL_DEALS).setLastModified(System.currentTimeMillis() - (4 * 24 * 60 * 60 * 1000L));
        Assertions.assertTrue(modified);

        List<Store> stores = storesFetcher.readAllShopsFromFile();

        Assertions.assertNotNull(stores);
        Assertions.assertTrue(stores.isEmpty());
    }



}