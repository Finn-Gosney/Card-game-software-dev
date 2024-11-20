

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Hand {

    private final List<Card> cards;        // Stores the player's cards
    private final ReentrantLock handLock;   // Ensures atomic actions
    private final int playerNo;

    public Hand (List<Card> cards, int playerNo)
    {
        this.cards = cards; 
        this.handLock = new ReentrantLock();
        this.playerNo = playerNo;
    }

    public List<Card> getHand()
    {
        return cards;
    }

    public int getPlayerNumber()
    {
        return playerNo;
    }
    
    public Card discardCard(int playerNo){
    /*
     * get the card to discard
     * ensure we dont discard cards equivalent to player number 
     * if there are no cards equal to player number, we discard a random card
     */
        Card cardToDiscard = null; 
        Random rand = new Random();
        List<Card> placeholderHand = cards;
        for(int i = 0; i < cards.size(); i++)
        {
            if (cards.get(i).getValue() == playerNo) 
            {
                placeholderHand.remove(i); //ensure we never remove the card equal to player number
            }
        }
        cardToDiscard = placeholderHand.get(rand.nextInt(placeholderHand.size())); //Randomly discard what is left
        return cardToDiscard;
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }
}

