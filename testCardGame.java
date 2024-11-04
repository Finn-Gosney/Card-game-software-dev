import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.main.CardGame;
import src.main.Player;
import src.main.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Using JUnit4 to test the CardGame class.
 */

public class testCardGame {

    private CardGame cardGame;
    private Path validTempFile;
    private Path minTempFile;

    @Before
    public void setUp() throws IOException {
        cardGame = new CardGame();
        // Create a temporary valid pack file to test with
        validTempFile = Files.createTempFile("four", ".txt");
        try (FileWriter writer = new FileWriter(validTempFile.toFile())) {
            // Write the necessary content to the file
            writer.write("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n32\n");
        }
        //Create a temporary file with the minimum number of cards
        minTempFile = Files.createTempFile("one", ".txt");
        try (FileWriter writer = new FileWriter(minTempFile.toFile())) {
            // Write the necessary content to the file
            writer.write("1\n2\n3\n4\n5\n6\n7\n8\n");
        }
    }

    @After
    public void tearDown() throws IOException {
        // Delete the temporary file after the test
        Files.deleteIfExists(validTempFile);
        Files.deleteIfExists(minTempFile);
    }

    @Test
    public void testStartWithValidPackFile() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(("4\n" + validTempFile.toString() + "\n").getBytes());
        System.setIn(in);
        cardGame.start();

        //Check if game starts correctly with a valid pack file
        System.setIn(sysInBackup);
    }

    @Test
    // Test if the game starts correctly with the minimum number of players to handle the edge case
    //Could lead to poor user performance if handled incorrectly
    public void testMinPlayers() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("1\n" + minTempFile.toString() + "\n").getBytes());
        System.setIn(in);

        cardGame.start();

        System.setIn(sysInBackup);
    }

    @Test
    // Test if game handles invalid player input correctly
    public void testInvalidPlayerInput() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        //Test invalid input followed by valid input
        ByteArrayInputStream in = new ByteArrayInputStream(("x\n4\n" + validTempFile.toString() + "\n").getBytes());
        System.setIn(in);
        //Capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        cardGame.start();
        // Verify the output contains the "Please enter a valid number" message
        String output = outContent.toString();
        assertTrue(output.contains("Please enter a valid number."));

        // Restore the original System.in and System.out
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }

    @Test
    // Test if game handles invalid pack file input correctly
    public void testInvalidPackFile() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        //Test invalid input followed by valid input
        ByteArrayInputStream in = new ByteArrayInputStream("4\ninvalidPackFile.txt\nfour.txt\n".getBytes());
        System.setIn(in);
        //Capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        cardGame.start();
        // Verify the output contains the "File not found. Please select a valid pack location." message
        String output = outContent.toString();
        assertTrue(output.contains("File not found. Please select a valid pack location."));

        // Restore the original System.in and System.out
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }


    // ----------------------------------------SHOULD NOT WORK YET ----------------------------------------
    /** 
    @Test
    public void testCreateDecks() {
        // For when create decks is finished
        int numberOfPlayers = 4;
        List<Deck> decks = cardGame.createDecks(numberOfPlayers);
        assertNotNull(decks);
        assertEquals(numberOfPlayers, decks.size());
    }

    @Test
    public void testAssignDecks() {
        // player needs to be constructed as well as assign decks
        int numberOfPlayers = 4;
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player" + (i + 1), null, null));
        }
        List<Deck> decks = cardGame.createDecks(numberOfPlayers);

        cardGame.assignDecks(players, decks);

        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = players.get(i);
            assertNotNull(player.getLeftDeck());
            assertNotNull(player.getRightDeck());
            assertEquals(decks.get(i), player.getLeftDeck());
            assertEquals(decks.get((i + 1) % numberOfPlayers), player.getRightDeck());
        }
    }
}
*/
}
