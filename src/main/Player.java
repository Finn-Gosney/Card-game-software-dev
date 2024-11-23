
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable

{

    private int playerNumber;
    private Deck leftDeck;
    private Deck rightDeck;
    private Hand hand;
    private boolean isRunning;
    private static final AtomicBoolean gameOver = new AtomicBoolean(false);

    public Player(int playerNumber, Deck leftDeck, Deck rightDeck) {
        this.playerNumber = playerNumber;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.isRunning = false;
    }

    public void run() {
        isRunning = true;
        while (isRunning && !gameOver.get()) {
            System.out.println("Player " + playerNumber + " is running...");
            try {
                if (checkVictory()) {
                    gameOver.set(true);
                    System.out.println("Player " + playerNumber + " wins! Notifying other players...");

                    break;
                }

                Card discardCard = checkDiscard();
                drawAndDiscard(discardCard);
                Thread.sleep(100);

            } catch (InterruptedException e) {
                System.out.println("Player " + playerNumber + " was interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Player " + playerNumber + " has stopped. Outputting to file...");
    }

    public void stopPlayerThread() {
        isRunning = false;
        Thread.currentThread().interrupt(); // blocks any more running code
    }

    private synchronized Card checkDiscard() {
        /*
         * this method gets the card we want to discard from our hands
         */
        if (hand == null) {
            throw new IllegalStateException("Hand has not been set for this player.");
        }
        return hand.cardToDiscard(playerNumber);
    }

    public void setHand(List<Hand> hands) {
        /*
         * Set this players hand to the corresponding hand
         */
        try {
            for (Hand hand : hands) {
                if (hand.getPlayerNumber() == playerNumber) {
                    this.hand = hand;
                    return; // break out of the method
                }
            }
            throw new NoCorrespondingHandsException("There is no corresponding hand"); // Throws if IDs do not
                                                                                       // correspond
        } catch (Player.NoCorrespondingHandsException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkVictory() {
        ArrayList<Card> cards = hand.getHand();
        System.out.println(cards.toString());
        return Card.areAllCardsEqual(cards);
    }

    private synchronized void drawAndDiscard(Card discardCard) {
        /*
         * in this method, we must, as a single action, draw from the left and discard
         * to the right
         */
        Card drawnCard = leftDeck.drawCard();
        rightDeck.discardCard(discardCard);
        hand.discardCard(discardCard);
        hand.addCard(drawnCard);
    }

    private static class NoCorrespondingHandsException extends Exception {
        public NoCorrespondingHandsException(String message) {
            super(message);
        }
    }
}
