
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class testCardGame {
    private CardGame game;
    private Deck leftDeck;
    private Deck rightDeck;

    @Before
    public void setUp() {
        game = new CardGame();
    }

    
    
    /*
     * Tests the getPlayerNumber method of the CardGame class.
     * Verifies that the number of players is correct when taken from the input stream.
     * Uses reflection to access the private method.
     */
    @Test
    public void testGetPlayerNumberValidInput() {
        String input = "3\n";
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

    /*
     * Tests the getPlayerNumber method of the CardGame class with an invalid input.
     * Verifies that the number of players is incorrect for an invalid input when taken from the input stream.
     * Uses reflection to access the private method.
     */
    @Test
    public void testGetPlayerNumberInvalidInput() {
        String input = "abc\n3\n";
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

    /* 
     * Tests the getPlayerNumber method of the CardGame class with a minimum number of players.
     * Verifies that the number of players is correct when taken from the input stream with the smallest value.
     * Uses reflection to access the private method.
     */
    @Test
    public void testMinGetPlayerNumber() {
        String input = "1";
        
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            int numPlayers = (int) getPlayerNumberMethod.invoke(game);
            assertEquals(1, numPlayers);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
    
    /*
     * Tests the getPlayerNumber method of the CardGame class with a null input.
     * Verifies that the number of players is correct when taken from the input stream with a null value.
     * Uses reflection to access the private method.
     */
    @Test
    public void testGetPlayerNumberNullInput() {
        String input = "\n3";
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

    /*
     * Tests the getValidPack method of the CardGame class.
     * Verifies that the pack is valid when taken from the input stream.
     * Uses reflection to access private fields and method.
     */
    @Test
    public void testGetValidPack() throws IOException {
        String playerinput = "2";
        System.setIn(new ByteArrayInputStream(playerinput.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            getPlayerNumberMethod.invoke(game);

            // Log the value of numPlayers because it is needed for the next method (getValidPack)
            Field numPlayersField = CardGame.class.getDeclaredField("numPlayers");
            numPlayersField.setAccessible(true);
            int numPlayers = numPlayersField.getInt(game);
            System.out.println("numPlayers: " + numPlayers);

            String packinput = "testPack.txt";
            System.setIn(new ByteArrayInputStream(packinput.getBytes()));

            Method getValidPackMethod = CardGame.class.getDeclaredMethod("getValidPack", int.class);
            getValidPackMethod.setAccessible(true);
            Object validPack = getValidPackMethod.invoke(game, numPlayers);
            assertNotNull(validPack); 
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }    
    }


    /*
     * Tests the getValidPack method of the CardGame class with an invalid file.
     * Verifies that an invalid pack is handled when taken from the input stream.
     * Uses reflection to access private fields and methods.
     */
    @Test
    public void testGetValidPackInvalidFile() {
        String playerInput = "2";
        System.setIn(new ByteArrayInputStream(playerInput.getBytes()));
        try {
            Method getPlayerNumberMethod = CardGame.class.getDeclaredMethod("getPlayerNumber");
            getPlayerNumberMethod.setAccessible(true);
            getPlayerNumberMethod.invoke(game);

            Field numPlayersField = CardGame.class.getDeclaredField("numPlayers");
            numPlayersField.setAccessible(true);
            int numPlayers = numPlayersField.getInt(game);

            // Simulate invalid pack input followed by valid pack input
            String invalidPackInput = "invalidPack.txt\n";
            String validPackInput = "testPack.txt\n";
            System.setIn(new ByteArrayInputStream((invalidPackInput + validPackInput).getBytes()));

            Method getValidPackMethod = CardGame.class.getDeclaredMethod("getValidPack", int.class);
            getValidPackMethod.setAccessible(true);
            Object validPack = getValidPackMethod.invoke(game, numPlayers);
            assertNotNull(validPack); 

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
        
    
    /*
     * Tests the createDecks method of the CardGame class.
     * Verifies that the decks are created correctly.
     * Uses reflection to access the private method.
     */
    @Test
    public void testCreateDecks() {
        try {
            Method createDecksMethod = CardGame.class.getDeclaredMethod("createDecks", ArrayList.class, int.class);
            createDecksMethod.setAccessible(true);

            ArrayList<Card> cards = new ArrayList<>();
            //Create 4 Card objects, 1 for each Deck
            cards.add(new Card(1));
            cards.add(new Card(2));
            cards.add(new Card(3));
            cards.add(new Card(4));
            
            int numPlayers = 4;

            createDecksMethod.invoke(game, cards, numPlayers);

            Field decksField = CardGame.class.getDeclaredField("decks");
            decksField.setAccessible(true);
            List<Deck> decks = (List<Deck>) decksField.get(game);

            assertNotNull(decks);
            assertEquals(numPlayers, decks.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
    


    /*
     * Tests the getEveryNthCard method of the CardGame class.
     * Verifies that the correct cards are selected.
     * Uses reflection to access the private method.
     */
    @Test
    public void getEveryNthCard() {
        try {
            CardGame game = new CardGame();

            Method getEveryNthCardMethod = CardGame.class.getDeclaredMethod("getEveryNthCard", ArrayList.class, int.class);
            getEveryNthCardMethod.setAccessible(true);

            ArrayList<Card> cards = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
            cards.add(new Card(i));
            }
            int startIndex = 1;

            Field numPlayersField = CardGame.class.getDeclaredField("numPlayers");
            numPlayersField.setAccessible(true);
            numPlayersField.setInt(game, 3);

            ArrayList<Card> result = (ArrayList<Card>) getEveryNthCardMethod.invoke(game, cards, startIndex);
            assertNotNull(result);
            assertEquals(2, result.get(0).getValue());
            assertEquals(5, result.get(1).getValue());
            assertEquals(8, result.get(2).getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the startPlayerThread method of the CardGame class.
     * Verifies that the player thread is started.
     * Uses reflection to access the private method.
     */
    @Test
    public void testStartPlayerThread() throws InterruptedException {
        try {
            leftDeck = new Deck(1);
            rightDeck = new Deck(2);

        
            leftDeck.addCards(new ArrayList<>(Arrays.asList(new Card(6), new Card(1))));
            rightDeck.addCards(new ArrayList<>(Arrays.asList(new Card(2), new Card(4))));

            Method startPlayerThreadMethod = CardGame.class.getDeclaredMethod("startPlayerThread", Player.class);
            startPlayerThreadMethod.setAccessible(true);

        
            Player player = new Player(1, leftDeck, rightDeck);

            startPlayerThreadMethod.invoke(game, player);

            Thread.sleep(10); // Give some time for the thread to start

            Field isRunningField = Player.class.getDeclaredField("isRunning");
            isRunningField.setAccessible(true);
            boolean isRunning = isRunningField.getBoolean(player);

            assertTrue(isRunning);
            player.stopPlayerThread();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception occurred: " + e.getMessage());
            }   

        
            
    } 
        
    
}


