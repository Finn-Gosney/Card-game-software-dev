
import java.util.Scanner;
import java.io.File;
import java.util.List;
 
public class Player implements Runnable

{   
    
    private int playerNumber;
    private Deck leftDeck;
    private Deck rightDeck;
    private boolean gameOver;
    private Hand hand;
    private boolean isRunning = false;

    public Player(int playerNumber, Deck leftDeck, Deck rightDeck) {
        this.playerNumber = playerNumber;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.gameOver = false;
    }


    public void run()
    {
        Card discardCard = null;
        isRunning = true;
         while (isRunning) {
            System.out.println("Player thread" + playerNumber + "is running...");
            try {
                checkVictory();
                if(gameOver = true)
                {
                    //TODO: notify all other threads and stop them
                    //print hand to a file
                }

                discardCard = checkDiscard();
                drawAndDiscard(discardCard);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread" + playerNumber + "was interrupted");
                break;
            }
        }
        System.out.println("Player thread" + playerNumber + "has stopped." + "\n Outputting to file");
    }

    
    public void stopPlayerThread()
    {
        isRunning = false;
        Thread.currentThread().interrupt(); //blocks any more running code
    }

    private Card checkDiscard()
    {
        /*
         * this method gets the card we want to discard from our hands
         */
        if (hand == null) {
        throw new IllegalStateException("Hand has not been set for this player.");
    }
        return hand.discardCard(playerNumber);
    }

    public void setHand(List<Hand> hands) {
        /*
         * Set this players hand to the corresponding hand
         */
        try {
        for (Hand hand : hands) {
            if (hand.getPlayerNumber() == playerNumber) {
                this.hand = hand;
                return; //break out of the method
            }
        }
            throw new NoCorrespondingHandsException("There is no corresponding hand"); //Throws if IDS do not correspond
        } catch (Player.NoCorrespondingHandsException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkVictory()
    {

        List<Card> cards = hand.getHand();
        if(Card.areAllCardsEqual(cards))
        {
            gameOver = true;
        }
        
    }

    private synchronized void drawAndDiscard(Card discardCard)
    {
        /*
        *in this method, we must, as a single action, draw from the left and discard to the right
        */

        Card drawnCard = leftDeck.drawCard();
        rightDeck.discardCard(discardCard);
        hand.addCard(drawnCard);
    }

    private static class NoCorrespondingHandsException extends Exception {
        public NoCorrespondingHandsException(String message) {
            super(message);
        }
    }
}
