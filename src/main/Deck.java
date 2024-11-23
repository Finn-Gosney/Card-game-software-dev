
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Deck {
    /*
     * This class is not threaded, but should be thread safe
     * through the use of locks and java collection queues.
     * Collections give us inbuilt methods in order to run
     * atomic actions that greatly simplify this class.
     */
    private ArrayList<Card> cards;
    private Lock deckLock;
    private int deckNo;
    private Queue<Card> cardQueue;

    public Deck(int deckNo) {
        this.deckNo = deckNo;
        this.cardQueue = null;
        this.cards = null;
    }

    public void addCards(ArrayList<Card> cards) {
        /*
         * This function is soley for the start of the game
         * after filling players hands, we fill the decks
         * with the remaning cards in the pack and convert
         * into a collections
         */
        this.cards = cards;
        initializeQueue(); // convert to a queue
        System.out.println("ADDCARDS" +cards);
    }

    public void initializeQueue() {
        // Convert from a list of cards to a queue of cards
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
        System.out.println("polling" + cards);
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
        File outputFile = new File("Deck " + deckNo + " Output.txt");
        try {
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(cards.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
