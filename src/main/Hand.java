
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Hand {

    private final ArrayList<Card> cards; // Stores the player's cards
    private final ReentrantLock handLock; // Ensures atomic actions
    private final int playerNo;

    public Hand(ArrayList<Card> cards, int playerNo) {
        this.cards = cards;
        this.handLock = new ReentrantLock();
        this.playerNo = playerNo;
    }

    public synchronized ArrayList<Card> getHand() {
        return cards;
    }

    public int getPlayerNumber() {
        return playerNo;
    }

    public Card cardToDiscard(int playerNo) {
        /*
         * get the card to discard
         * ensure we dont discard cards equivalent to player number
         * if there are no cards equal to player number, we discard a random card
         */
        Card cardToDiscard = null;
        Random rand = new Random();
        int count = 0;
        ArrayList<Card> placeholderHand = new ArrayList<>(); // We need to not
        for (Card card : cards) // have a reference
        { // but a seperate list
            placeholderHand.add(card);
        }
        for (int i = 0; i < cards.size(); i++) {

            if (placeholderHand.get(count).getValue() == playerNo) {
                placeholderHand.remove(count); // ensure we never remove the card equal to player number
            } else {
                count++;
            }
        }
        if (count > 3) {
            System.out.println(cards);
        }
        cardToDiscard = placeholderHand.get(rand.nextInt(placeholderHand.size())); // Randomly discard what is left
        return cardToDiscard;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void discardCard(Card card) {
        cards.remove(card);
    }
}
