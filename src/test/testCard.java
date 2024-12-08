import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import static org.junit.Assert.*;


@RunWith(JUnit4.class)

public class testCard {

    /*
     * Tests the getValue method of the Card class.
     * Verifies that the value returned by getValue is correct.
     */
    @Test
    public void testGetValue() {
        Card card = new Card(5);
        int value = card.getValue();
        System.out.println("Card value: " + value);
        assertEquals(5, value);
    }

    /*
     * Tests the isEqual method of the Card class.
     * Verifies that two cards with the same value are equal.
     * Also verifies that two cards with different values are not equal.
     */
    @Test
    public void testIsEqual() {
        Card card1 = new Card(1);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        assertTrue(card1.isEqual(card2));
        assertFalse(card1.isEqual(card3));
        assertFalse(card1.isEqual(null));
    }
   
    /*
     * Tests the isEqual method of the Card class with negative values.
     * Verifies that two cards with the same negative value are equal.
     */
    @Test 
    public void testNegativeIsEqual() {
        Card card1 = new Card(-1);
        Card card2 = new Card(-1);
        assertTrue(card1.isEqual(card2));
    }

    /*
     * Tests the isEqual method of the Card class with the maximum integer limit.
     * Verifies that two cards with the same maximum integer value are equal.
     */
    @Test
    public void testMaxBoundaryIsEqual() {
        Card card1 = new Card(Integer.MAX_VALUE);
        Card card2 = new Card(Integer.MAX_VALUE);
        assertTrue(card1.isEqual(card2));
    }

    /*
     * Tests the isEqual method of the Card class with the minimum integer limit.
     * Verifies that two cards with the same minimum integer value are equal.
     */
    @Test 
    public void testMinBoundaryIsEqual() {
        Card card1 = new Card(Integer.MIN_VALUE);
        Card card2 = new Card(Integer.MIN_VALUE);
        assertTrue(card1.isEqual(card2));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class.
     * Verifies that an array list of cards with the same value are all equal.
     */
    @Test
    public void testAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(1));
        cards.add(new Card(1));
        assertTrue(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class.
     * Verifies that an array list of cards with different values are not all equal.
     */
    @Test
    public void testInvalidAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(2));
        cards.add(new Card(3));
        assertFalse(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class with an empty arraylist.
     * Checks to see if the method handles an empty arraylist correctly.
     */
    @Test
    public void testEmptyAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        assertFalse(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class with a single card.
     * Verifies that a single card is equal to itself.
     * Checks to see if the method can handle only one card.
     */
    @Test
    public void testSingleAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1));
        assertTrue(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class with negative values.
     * Verifies that a list of cards with the same negative value are all equal.
     * Ensures negative values are handled correctly
     */
    @Test
    public void testNegativeAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(-1));
        cards.add(new Card(-1));
        cards.add(new Card(-1));
        assertTrue(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class with the maximum integer limit.
     * Verifies that a list of cards with the same maximum integer value are all equal.
     * Ensures the maximum integer limit is handled correctly.
     */
    @Test
    public void testMaxBoundaryAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Integer.MAX_VALUE));
        cards.add(new Card(Integer.MAX_VALUE));
        cards.add(new Card(Integer.MAX_VALUE));
        assertTrue(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the areAllCardsEqual method of the Card class with the minimum integer limit.
     * Verifies that a list of cards with the same minimum integer value are all equal.
     * Ensures the minimum integer limit is handled correctly.
     */
    @Test
    public void testMinBoundaryAreAllCardsEqual() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Integer.MIN_VALUE));
        cards.add(new Card(Integer.MIN_VALUE));
        cards.add(new Card(Integer.MIN_VALUE));
        assertTrue(Card.areAllCardsEqual(cards));
    }

    /*
     * Tests the toString method of the Card class.
     * Verifies that the string representation of a card is correct.
     */
    @Test
    public void testToString() {
        Card card = new Card(5);
        assertEquals("5", card.toString());
    }


}