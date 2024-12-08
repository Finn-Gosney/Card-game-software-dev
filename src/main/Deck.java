
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Deck {
    /*
     * This class uses collections to clean up drawing and discard cards
     * These methods are synchronised to ensure thread safety
     */
    private ArrayList<Card> cards;
    private int deckNo;
    private Queue<Card> cardQueue;

    public Deck(int deckNo) {
        this.deckNo = deckNo;
        this.cardQueue = null;
        this.cards = null;
    }

    public void addCards(ArrayList<Card> cards) {
        /*
         * At the start of the game, fill up the decks with cards 
         */
        this.cards = cards;
        initializeQueue(); // convert to a queue
    }

    public void initializeQueue() {
        /*
        * Convert from a list of cards to a queue of cards
        */ 
        if (this.cardQueue == null) {
            this.cardQueue = new ArrayDeque<>(cards);
        } else {
            throw new IllegalStateException("Queue has already been initialized.");
        }
    }

    public synchronized Card drawCard() {
        /*
         * We lock this deck by it being synchronised (locks this class)
         * then we want to use collections inbuilt methods to return the head
         * of the queue
         */
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        return this.cardQueue.poll(); // Remove and return the head of the queue
    }

    public synchronized void discardCard(Card card) {
        /*
         * Again we lock this deck by it using synchronised methods
         */
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        this.cardQueue.offer(card); // Add card to the tail of the queue
    }

    public synchronized Queue<Card> getQueue() {
        if (this.cardQueue == null) {
            throw new IllegalStateException("Queue is not initialized. Call initializeQueue() first.");
        }
        return this.cardQueue;
    }

    public void endSequence() {
        /*
         * create a file and write the correct contents to it
         */
        File outputFile = FileCreator.createFile(deckNo, true); //This file only needs to be created at the end
        //because it does not need to output initial values 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(cards.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
