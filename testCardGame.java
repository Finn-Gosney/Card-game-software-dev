import src.main.CardGame;
import src.main.Deck;
import src.main.Hand;
import src.main.Player;
import src.main.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
    // Tests when a user inputs a correct pack
    public void testGetValidPack() throws IOException {
        String input = "2\ntestPack.txt\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            //Double check that the game object is not null
            assertNotNull(game);
            // Call getPlayerNumberMethod
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            getPlayerNumberMethod.invoke(game);

            // Log the value of numPlayers becuase I need it for the next method
            Field numPlayersField = CardGame.class.getDeclaredField("numPlayers");
            numPlayersField.setAccessible(true);
            int numPlayers = numPlayersField.getInt(game);
            System.out.println("numPlayers: " + numPlayers);

            Method getValidPackMethod = CardGame.class.getDeclaredMethod("getValidPack");
            getValidPackMethod.setAccessible(true);
            Object validPack = getValidPackMethod.invoke(game);
            assertNull(validPack);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException| NoSuchFieldException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    
    @Test
    // Tests when a user inputs a file that doesnt exst
    public void testGetValidPackInvalidFile() {
        String input = "2\ninvalidPack.txt\ntestPack.txt\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            getPlayerNumberMethod.invoke(game);

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
    public void testCreateDecks() {
        int numPlayers = 4;
        //Pass null for cards as not used in createDecks
        game.createDecks(null, numPlayers);
        List<Deck> decks = game.getDecks();
        assertNotNull(decks);
        assertEquals(numPlayers, decks.size());

        for (int i = 0; i < numPlayers; i++) {
            assertEquals(i, decks.get(i).getId());
        }
    }

    @Test
    public void testAssignDecks() {
            
    }

    @Test
    public void testDeckDistribution() throws Exception {
        Method deckDistributionMethod = CardGame.class.getDeclaredMethod("deckDistribution", List.class, List.class);
        deckDistributionMethod.setAccessible(true);

        deckDistributionMethod.invoke(cardGame, decks, cards);

        for (int i = 0; i < decks.size(); i++) {
            Deck deck = deck.get(i);
            List<Card> deckCards = deck.getCards();
            assertEquals(4, deckCards.size());
            for (int j = 0; j < deckCards.size(); j++) {
                assertEquals(i + j * 4, deckCards.get(j).getcardValue());
            }
        }

    }
*/
    @Test
    public void testStartPlayerThread() {
        try {
            Method startPlayerThreadMethod = CardGame.class.getDeclaredMethod("startPlayerThread");
            startPlayerThreadMethod.setAccessible(true); 

            // Start the thread
            startPlayerThreadMethod.invoke(game);

            //Access the playerThread field from CardGame
            Field playerThreadField = CardGame.class.getDeclaredField("playerThread");
            playerThreadField.setAccessible(true); // Make the field accessible

            // Get the playerThread object
            Thread playerThread = (Thread) playerThreadField.get(game);

            
            assertNotNull(playerThread);
            assertTrue(playerThread.isAlive());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        } 
    }
    


}
