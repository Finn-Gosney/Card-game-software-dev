
import java.util.ArrayList;

public class CardManager {
    /*
     * This class is used to generate the cards
     * to be distributed and that is all
     * I just wanted to abstract it away
     * because it is not nice
     */

    public ArrayList<Card> generateCards(int[] cardNumbers) {
        ArrayList<Card> cards = new ArrayList<>();

        for (int numbers : cardNumbers) {
            cards.add(new Card(numbers));
        }
        return cards;
    }
}
