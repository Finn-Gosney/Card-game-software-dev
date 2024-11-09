import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Hand {
    /*
     * Cannot really be nested as we 
     * need to get the cards into 
     * these hands without going
     * through player, it just
     * needs to have a player linked
     * to it which is done in player's
     * constructor
     */
    private final List<Card> cards;        // Stores the player's cards
    private final ReentrantLock handLock;   // Ensures atomic actions
    private final int playerNo;

    public Hand (List<Card> cards, int playerNo)
    {
        this.cards = cards; 
        this.handLock = new ReentrantLock();
        this.playerNo = playerNo;
    }
}
