import java.util.Scanner;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

public class Deck implements Runnable
{
    private List<Card> cards;
    private Lock deckLock;
    private int deckNo;

    public void run()
    {}

    public Deck(int deckNo)
    {
        this.deckNo = deckNo;
    }

    public void addCards(List<Card> cards)
    {
        /*
         * This function is soley for the start of the game 
         * after filling players hands, we fill the decks 
         * with the remaning cards in the pack
         * 
         */

         //TODO: Make this work with threading 
         //Currently cannot make it distrubte 
         //in a round robin due to the lack of threading
    }
}