import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class testCardManager {

    @Test
    /*
     * Tests the generateCards method of the CardManager class.
     * Verfies that the cards genderated have the correct values.
     */
    public void testGenerateCardsWithValidArray() {
        CardManager cardManager = new CardManager();
        int[] cardNumbers = {1, 2, 3, 4, 5};
        ArrayList<Card> cards = cardManager.generateCards(cardNumbers);

        assertEquals(5, cards.size());
        for (int i = 0; i < cardNumbers.length; i++) {
            assertEquals(cardNumbers[i], cards.get(i).getValue());
        }
    }

    /*
     * Tests the generateCards method of the CardManager class with an empty array.
     * Verfies that the method returns an empty class. 
     */
    @Test
    public void testGenerateCardsWithEmptyArray() {
        CardManager cardManager = new CardManager();
        int[] cardNumbers = {};
        ArrayList<Card> cards = cardManager.generateCards(cardNumbers);

        assertTrue(cards.isEmpty());
    }

    /*
     * Tests the generatedCards method of the CardManager class with duplicate values.
     * Verfies that the cards generates have the correct values, wih duplicate cardNumbers.
     */
    @Test
    public void testGenerateCardsWithDuplicateValues() {
        CardManager cardManager = new CardManager();
        int[] cardNumbers = {1, 2, 2, 3, 3, 3};
        ArrayList<Card> cards = cardManager.generateCards(cardNumbers);

        assertEquals(6, cards.size());
        for (int i = 0; i < cardNumbers.length; i++) {
            assertEquals(cardNumbers[i], cards.get(i).getValue());
        }
    }

    /*
     * Test the generateCars method of the CardManager class with negative values.
     * Verifies that the cards generated have the correct values.
     * Ensures negative values are handled correctly.
     */
    @Test
    public void testGenerateCardsWithNegativeValues() {
        CardManager cardManager = new CardManager();
        int[] cardNumbers = {-1, -2, -3};
        ArrayList<Card> cards = cardManager.generateCards(cardNumbers);

        assertEquals(3, cards.size());
        for (int i = 0; i < cardNumbers.length; i++) {
            assertEquals(cardNumbers[i], cards.get(i).getValue());
        }
    }

    /*
     * Tests the generateCards method of the CardManager class with boundary values.
     * Verfies that the cards generated have the correct values.
     * Tests both the Minimum and Maximum values are handled correctly.
     */
    @Test
    public void testGenerateCardsWithBoundaryValues() {
        CardManager cardManager = new CardManager();
        int[] cardNumbers = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        ArrayList<Card> cards = cardManager.generateCards(cardNumbers);

        assertEquals(2, cards.size());
        assertEquals(Integer.MAX_VALUE, cards.get(0).getValue());
        assertEquals(Integer.MIN_VALUE, cards.get(1).getValue());
    }
}