import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class testHand {

    private Hand hand;
    private ArrayList<Card> cards;

    /*
     * Sets up the test environment.
     * Creates a hand with three cards.
     */
    @Before
    public void setUp() {
        cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(2));
        cards.add(new Card(3));
        hand = new Hand(cards, 1);
    }

    /*
     * Test the initialization of the Hand class.
     * Verifies that the hand is not null and has three cards.
     */
    @Test
    public void testHandInitialization() {
        assertNotNull(hand);
        assertEquals(3, hand.getHand().size());
        assertEquals(1, hand.getPlayerNumber());
    }

    /*
     * Tests the getHand method of the Hand class.
     * Verifies that the hand has the correct cards.
     */
    @Test
    public void testGetHand() {
        ArrayList<Card> handCards = hand.getHand();
        assertEquals(3, handCards.size());
        assertEquals(1, handCards.get(0).getValue());
        assertEquals(2, handCards.get(1).getValue());
        assertEquals(3, handCards.get(2).getValue());
    }

    /*
     * Tests the getPlayerNumber method of the Hand class.
     * Verifies that the player number is correct.
     */
    @Test
    public void testGetPlayerNumber() {
        assertEquals(1, hand.getPlayerNumber());
    }

    /*
     * Tests the addCard method of the Hand class.
     * Verifies that the card is added to the hand.
     */
    @Test
    public void testAddCard() {
        Card newCard = new Card(4);
        hand.addCard(newCard);
        assertEquals(4, hand.getHand().size());
        assertTrue(hand.getHand().contains(newCard));
    }

    /*
     * Tests the discardCard method of the Hand class.
     * Verifies that the card is discarded from the hand.
     */
    @Test
    public void testDiscardCard() {
        hand.discardCard(cards.get(0));
        assertEquals(2, hand.getHand().size());
    }

    /*
     * Tests the discardCard method of the Hand class with a card that is not in the hand.
     * Verifies that the hand is not modified.
     */
    @Test
    public void testCardToDiscard() {
        Card cardToDiscard = hand.cardToDiscard(1);
        assertNotNull(cardToDiscard);
        assertNotEquals(1, cardToDiscard.getValue());
        assertTrue(hand.getHand().contains(cardToDiscard));
    }

    /*
     * Tests the discardCard method of the Hand class with a card that is not in the hand.
     * Verifies that the hand is not modified.
     */
    @Test
    public void testCardToDiscardWithNoPlayerNumberCard() {
        Hand newHand = new Hand(new ArrayList<>(cards), 4);
        Card cardToDiscard = newHand.cardToDiscard(4);
        assertNotNull(cardToDiscard);
        assertTrue(newHand.getHand().contains(cardToDiscard));
    }
}