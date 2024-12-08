import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

@RunWith(JUnit4.class)
public class testPlayer {
    private Player player;
    private Deck leftDeck;
    private Deck rightDeck;
    private Hand hand;

    /*
     * Sets up the test environment.
     * Creates a player as well as two decks and two cards in each deck.
     */
    @Before
    public void setUp() {
        leftDeck = new Deck(1);
        rightDeck = new Deck(2);

        
        leftDeck.addCards(new ArrayList<>(Arrays.asList(new Card(6), new Card(1))));
        rightDeck.addCards(new ArrayList<>(Arrays.asList(new Card(2), new Card(4))));

        
        player = new Player(1, leftDeck, rightDeck);

    }
    
    /*
     * Cleans up the test environment.
     * Deletes the output file if it exists.    
     */
    @After
    public void tearDown() {
        File outputFile = new File("Player 1 Output.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    /*
     * Tests the setHand method of the Player class.
     * Verifies that the hand is set correctly.
     */
    @Test
    public void testSetHand() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(1));
        cards.add(new Card(1));
        Hand hand = new Hand(cards, 1);

        List<Hand> hands = new ArrayList<>();
        hands.add(hand);
        player.setHand(hands);
        assertEquals(3, hand.getHand().size());
    }

    /*
     * Tests the checkVictory method of the Player class.
     * Verifies that the player has won the game with a valid winning hand.
     */
    @Test
    public void testValidCheckVictory() {
        try {
            
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(new Card(1));
            cards.add(new Card(1));
            cards.add(new Card(1));
            Hand hand = new Hand(cards, 1);
            player.setHand(List.of(hand));

            Method checkVictoryMethod = Player.class.getDeclaredMethod("checkVictory");
            checkVictoryMethod.setAccessible(true);
            boolean result = (boolean) checkVictoryMethod.invoke(player);
            assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the checkVictory method of the Player class.
     * Verifies that the player has not won the game with an invalid hand.
     */
    @Test
    public void testInvalidCheckVictory() {
        try {
            ArrayList<Card> cards = new ArrayList<>();
                cards.add(new Card(1));
                cards.add(new Card(2));
                cards.add(new Card(3));
                Hand hand = new Hand(cards, 1);
                player.setHand(List.of(hand));

                Method checkVictoryMethod = Player.class.getDeclaredMethod("checkVictory");
                checkVictoryMethod.setAccessible(true);
                boolean result = (boolean) checkVictoryMethod.invoke(player);
                assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the checkVictory method of the Player class.
     * Verifies that a player with a wining hand that does not correspond to their number can still win.
     * Ensures that the checkVictory method is not dependent on the player number.
     */
    @Test
    public void testNonPlayerNumberCheckVictory() {
        try {
            ArrayList<Card> cards = new ArrayList<>();
                cards.add(new Card(2));
                cards.add(new Card(2));
                cards.add(new Card(2));
                Hand hand = new Hand(cards, 1);
                player.setHand(List.of(hand));

                Method checkVictoryMethod = Player.class.getDeclaredMethod("checkVictory");
                checkVictoryMethod.setAccessible(true);
                boolean result = (boolean) checkVictoryMethod.invoke(player);
                assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the checkVictory method of the Player class.
     * Verifies that the player has won the game with a hand of the maximum possible value.
     * Ensures that the checkVictory method can handle the maximum integer value.
     */
    @Test
    public void testMaxCheckVictory() {
        try {
            ArrayList<Card> cards = new ArrayList<>();
                cards.add(new Card(Integer.MAX_VALUE));
                cards.add(new Card(Integer.MAX_VALUE));
                cards.add(new Card(Integer.MAX_VALUE));
                Hand hand = new Hand(cards, 1);
                player.setHand(List.of(hand));

                Method checkVictoryMethod = Player.class.getDeclaredMethod("checkVictory");
                checkVictoryMethod.setAccessible(true);
                boolean result = (boolean) checkVictoryMethod.invoke(player);
                assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the checkVictory method of the Player class.
     * Verifies that the player has won the game with a hand of the minimum possible value.
     * Ensures that the checkVictory method can handle the minimum integer value.
     */
    @Test
    public void testMinCheckVictory() {
        try {
            ArrayList<Card> cards = new ArrayList<>();
                cards.add(new Card(Integer.MIN_VALUE));
                cards.add(new Card(Integer.MIN_VALUE));
                cards.add(new Card(Integer.MIN_VALUE));
                Hand hand = new Hand(cards, 1);
                player.setHand(List.of(hand));

                Method checkVictoryMethod = Player.class.getDeclaredMethod("checkVictory");
                checkVictoryMethod.setAccessible(true);
                boolean result = (boolean) checkVictoryMethod.invoke(player);
                assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the checkDiscard method of the Player class.
     * Verifies that the player discards a card that is not equal to their correspomdimg number.
     */
    @Test
    public void testCheckDiscard() {
        try{
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(new Card(1));
            cards.add(new Card(2));
            cards.add(new Card(3));
            Hand hand = new Hand(cards, 1);
            player.setHand(List.of(hand));
        
            Method checkDiscardMethod = Player.class.getDeclaredMethod("checkDiscard");
            checkDiscardMethod.setAccessible(true);

            Card discardCard = (Card) checkDiscardMethod.invoke(player);
            assertNotNull(discardCard);
            assertNotEquals(1, discardCard.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }

    }

    /*
     * Tests the drawAndDiscard method of the Player class.
     * Verifies that the player draws a card from the left deck and discards a card to the right deck.
     */
    @Test
    public void testDrawAndDiscard() {
        try {
            
            Method drawAndDiscardMethod = Player.class.getDeclaredMethod("drawAndDiscard", Card.class);
            drawAndDiscardMethod.setAccessible(true);

            
            Method checkDiscardMethod = Player.class.getDeclaredMethod("checkDiscard");
            checkDiscardMethod.setAccessible(true);

            
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(new Card(1));
            cards.add(new Card(2));
            cards.add(new Card(1));
            Hand hand = new Hand(cards, 1);
            player.setHand(List.of(hand));

           
            Card discardCard = (Card) checkDiscardMethod.invoke(player);

            
            drawAndDiscardMethod.invoke(player, discardCard);

            List<Integer> cardValues = hand.getHand().stream()
            .map(Card::getValue)
            .collect(Collectors.toList());
            
            assertFalse(hand.getHand().contains(discardCard));
            assertEquals(3, hand.getHand().size());
            assertTrue(cardValues.contains(6)); //The card that is drawn from the left deck
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the endSequence method of the Player class.
     * Verifies that the player writes the output to a file.
     */
    @Test
    public void testEndSequence() {
        try {
            Method endSequenceMethod = Player.class.getDeclaredMethod("endSequence");
            endSequenceMethod.setAccessible(true);

            ArrayList<Card> cards = new ArrayList<>();
            cards.add(new Card(1));
            cards.add(new Card(1));
            cards.add(new Card(1));
            hand = new Hand(cards, 1);

            player.setHand(List.of(hand));
            endSequenceMethod.invoke(player);

            File outputFile = new File("Player 1 Output.txt");
            assertTrue(outputFile.exists());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the run method of the Player class.
     * Verifies that the player thread is stopped after the player stops.
     */
    @Test
    public void testRunMethod() throws InterruptedException {
        Thread playerThread = new Thread(player);
        playerThread.start();
        Thread.sleep(10);
        player.stopPlayerThread();
        playerThread.join();
        try {
            Field runningField = Player.class.getDeclaredField("isRunning");
            runningField.setAccessible(true);
            boolean running = runningField.getBoolean(player);
            assertFalse(running);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    /*
     * Tests the stopPlayerThread method of the Player class.
     * Verifies that the player thread is stopped.
     */
    @Test
    public void testStopPlayerThread() {
        player.stopPlayerThread();
        try {
            Field isRunningField = Player.class.getDeclaredField("isRunning");
            isRunningField.setAccessible(true);
            boolean isRunning = isRunningField.getBoolean(player);
            assertFalse(isRunning);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }
}