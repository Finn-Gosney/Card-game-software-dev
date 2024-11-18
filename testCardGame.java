import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Using JUnit5 to test the CardGame class.
 */

public class testCardGame {

    /**
     * Runs before each test method.
     * Initializes the decks, players and numPlayers fields.
     * Ensures a clean state for each test.
     */
    @BeforeEach
    public void setUp() {
        CardGame.decks = new ArrayList<>();
        CardGame.players = new ArrayList<>();
        CardGame.numPlayers = 0;
    }

    @Test
    public void testCreateDecks() {
        CardGame.numPlayers = 4;
        CardGame.createDecks();
        assertEquals(4, CardGame.decks.size(), "Number of decks should be equal to number of players");
    }

    @Test
    public void testAssignDecks() {
        CardGame.numPlayers = 4;
        CardGame.createDecks();
        CardGame.assignDecks();
        assertEquals(4, CardGame.players.size(), "Number of players should be equal to number of players");
        for (int i = 0; i < CardGame.numPlayers; i++) {
            Player player = CardGame.players.get(i);
            assertNotNull(player, "Player should not be null");
        }
    }

    @Test
    public void testMain() {
        String input = "4\npath/to/valid/pack.txt\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mock the file reading part
        // You can use a mocking framework like Mockito to mock BufferedReader and FileReader

        // Call the main method
        CardGame.main(new String[]{});

        assertEquals(4, CardGame.numPlayers, "Number of players should be 4");
        assertEquals(4, CardGame.decks.size(), "Number of decks should be 4");
        assertEquals(4, CardGame.players.size(), "Number of players should be 4");
    }
}