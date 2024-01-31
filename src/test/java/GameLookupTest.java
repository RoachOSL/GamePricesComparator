import dev.Roach.GameLookup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class GameLookupTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testGiveTitleToGetListOFDealsWithStoresWithValidTitle() {
        GameLookup gameLookup = new GameLookup();

        String validTitle = "callofdutyblackops";

        gameLookup.giveTitleToGetListOFDealsWithStores(validTitle);

        String output = outContent.toString();

        assertTrue(output.contains("""
                Game: Call of Duty: Black Ops
                Cheapest Price Ever: 19.59
                                
                Deals:
                Store ID: 1
                Deal ID: BAUTek6nS3H6PqyM4miWzmLpbZ6rkPEgk8Jue2Ji8IY%3D
                Price: 39.99
                Retail Price: 39.99
                Savings: 0.000000           
                """));
    }

    @Test
    public void testGiveTitleToGetListOFDealsWithStoresWithNullOrEmptyTitle() {

        GameLookup gameLookup = new GameLookup();

        gameLookup.giveTitleToGetListOFDealsWithStores(null);

        String output = outContent.toString();

        assertTrue(output.contains("Game title cannot be null or empty."));

        gameLookup.giveTitleToGetListOFDealsWithStores("");

        String outputOfEmptyTitle = outContent.toString();

        assertTrue(outputOfEmptyTitle.contains("Game title cannot be null or empty."));
    }

    @Test
    public void testGiveTitleToGetListOFDealsWithStoresWithIncorrectTitle() {

        GameLookup gameLookup = new GameLookup();

        gameLookup.giveTitleToGetListOFDealsWithStores("randomTitle");

        String output = outContent.toString();

        assertTrue(output.contains("No exact match found for the title: randomTitle"));
    }

}


