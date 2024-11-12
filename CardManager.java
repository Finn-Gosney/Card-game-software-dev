import java.util.ArrayList;
import java.util.List;

public class CardManager {
    /*
     * This class is used to generate the cards 
     * to be distributed and that is all 
     * I just wanted to abstract it away 
     * because it is disgusting
     */

    public List<Card> generateCards(int[] cardNumbers)
    {
        List<Card> cards = new ArrayList<>();
    
    
    for(int numbers : cardNumbers)
        {
            cards.add(new Card(numbers));
        }
        return cards;
    }

    
}