
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Deck implements Runnable
{
    /*
     * This class is not threaded, but should be thread safe
     * through the use of locks and java collection queues.
     * Collections give us inbuilt methods in order to run 
     * atomic actions that greatly simplify this class.
     */
    private List<Card> cards;
    private Lock deckLock;
    private int deckNo;
    private Queue<Card> cardQueue;

    public void run()
    {}

    public Deck(int deckNo)
    {
        this.deckNo = deckNo;
        this.cardQueue = null;
        this.cards = null;
    }

    public void addCards(List<Card> cards)
    {
        /*
         * This function is soley for the start of the game 
         * after filling players hands, we fill the decks 
         * with the remaning cards in the pack and convert
         * into a collections
         */
         this.cards = cards;
         initializeQueue(); //convert to a queue
    }
    
    public void initializeQueue() {
        //Convert from a list of cards to a queue of cards
        if (this.cardQueue == null) {
            this.cardQueue = new ArrayDeque<>(cards); 
            this.cards = null; // Clear the temporary list reference
        } else {
            throw new IllegalStateException("Queue has already been initialized.");
        }
    }

    
    public Card drawCard() {
        /*
         * We want to lock this deck such that only only player can access at a time
         * then we want to use collections inbuilt methods to return the head 
         * of the queue
         */
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        return this.cardQueue.poll(); // Remove and return the head of the queue
    }

    public void discardCard(Card card) {
        /*
         * Again we need to lock the deck in order to stop 2 players accessing it at the
         * same time, then we want to add the card from hand back
         */
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        this.cardQueue.offer(card); // Add card to the tail of the queue
    }

    // Optional: Get the current state of the queue
    public Queue<Card> getQueue() {
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        return this.cardQueue;
    }
}