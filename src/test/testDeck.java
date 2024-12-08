import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;

@RunWith(JUnit4.class)
public class testDeck {

    private Deck deck;
    private ArrayList<Card> cards;

    @Before
    public void setUp() {
        deck = new Deck(1);
        cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(2));
        cards.add(new Card(3));
    }

    @After
    public void tearDown() {
        File outputFile = new File("Deck 1 Output.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }


    /**
     * Tests the initialization of the Deck class.
     * Verifies that the deck is not null.
     */
    @Test
    public void testDeckInitialization() {
        assertNotNull(deck);
    }

    /**
     * Tests the addCards method of the Deck class.
     * Verfies that the cards are added to the deck.
     */
    @Test
    public void testAddCards() {
        deck.addCards(cards);
        Queue<Card> cardQueue = deck.getQueue();
        assertNotNull(cardQueue);
        assertEquals(3, cardQueue.size());
    }

    /*
     * Tests the drawCard method of the Deck class.
     * Verifies that the card is drawn from the deck.
     */
    @Test
    public void testDrawCard() {
        deck.addCards(cards);
        Card drawnCard = deck.drawCard();
        assertEquals(1, drawnCard.getValue());
        assertEquals(2, deck.getQueue().size());
    }

    /*
     * Tests the discards the card method of the Deck class.
     * Verifies that the card is discarded from the deck.
     */
    @Test
    public void testDiscardCard() {
        deck.addCards(cards);
        Card card = new Card(4);
        deck.discardCard(card);
        assertEquals(4, deck.getQueue().size());
        assertTrue(deck.getQueue().contains(card));
    }

    /*
     * Tests the initializeQueue method of the Deck class.
     * Verifies that the deck is initialized.
     */
    @Test(expected = IllegalStateException.class)
    public void testQueueInitialization() {
        deck.addCards(cards);
        deck.initializeQueue();
    }

    /*
     * Tests the endSequence method of the Deck class.
     * Verifies that the output file is created.
     */
    @Test
    public void testEndSequence() {
        deck.addCards(cards);
        deck.endSequence();
        File outputFile = new File("Deck 1 Output.txt");
        assertTrue(outputFile.exists());
    }
}