
import java.util.Scanner;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
 
public class Player implements Runnable

{   
    
    private int playerNumber;
    private Deck leftDeck;
    private Deck rightDeck;
    private boolean gameOver;
    private Lock playerLock;
    private Hand hand;

    public Player(int playerNumber, Deck leftDeck, Deck rightDeck, List<Card> cards) {
        this.playerNumber = playerNumber;
        this.playerLock = new ReentrantLock();
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }


    public void run()
    {}
    

}