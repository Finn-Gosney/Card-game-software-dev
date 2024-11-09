import src.main.CardGame;
import src.main.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class testCardGame {
    private CardGame game;

    @Before
    public void setUp() {
        game = new CardGame();
    }

    @Test
    public void testGameStart() {
        //working on making a test for the start method
        game.start();
    }
    
    @Test
    // tests the getPlayerNumber method using reflection to access the private method
    public void testGetPlayerNumberValidInput() {
        // Set up the input stream and simulate user input
        //In this case the user will input the number 3 for 3 players
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            // Use reflection to access the getPlayerNumber method from CardGame
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true); // Make the method accessible
            int numPlayers = (int) getPlayerNumberMethod.invoke(game);
            assertEquals(3, numPlayers);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGetPlayerNumberInvalidInput() {
        String input = "abc\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            // As before, using reflection to access the private method
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            int numPlayers = (int) getPlayerNumberMethod.invoke(game);
            assertEquals(3, numPlayers);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }


    }

    @Test
    // Tests when a user inputs the minimum possible
    public void testMinGetPlayerNumber() {
        String input = "1\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            int numPlayers = (int) getPlayerNumberMethod.invoke(game);
            assertEquals(3, numPlayers);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetPlayerNumberNullInput() {
        String input = "\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            int numPlayers = (int) getPlayerNumberMethod.invoke(game);
            assertEquals(3, numPlayers);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    
    @Test
    public void testGetValidPack() throws IOException {
       // Use reflection to access the private method
       try {
            Method getValidPackMethod = CardGame.class.getDeclaredMethod("getValidPack");
            getValidPackMethod.setAccessible(true);
            Object validPack = getValidPackMethod.invoke(game);
            assertNull(validPack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    } 

    
    @Test
    //test when a user inputs a file that doesnt exst
    public void testGetValidPackInvalidFile() {
        String input = "invalid_pack.txt\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Method getValidPackMethod = CardGame.class.getDeclaredMethod("getValidPack");
            getValidPackMethod.setAccessible(true);
            Object validPack = getValidPackMethod.invoke(game);
            assertNull(validPack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
        
    }
    
    /* 
    @Test
    //Test when a user inputs a file that has the wrong number of cards but exists
    public void testGetValidPackWrongNumberOfCards() throws IOException {
        // Create a temporary file with invalid pack data
        File tempFile = File.createTempFile("invalidPack", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            for (int i = 0; i < 10; i++) {
                writer.write((i + 1) + "\n");
            }
        }

        String input = tempFile.getAbsolutePath() + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        List<Card> cards = game.getValidPack();
        assertNull(cards);

        // Clean up
        tempFile.delete();
    }
}   */
    
}