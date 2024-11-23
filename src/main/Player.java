
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        /*
         * main run function for the thread
         */
        isRunning = true;
        while (isRunning && !gameOver.get()) { // Loop through untill someone wins
            System.out.println("Player " + playerNumber + " is running...");
            try {
                if (checkVictory()) { // Run this if statement if check victory is true
                    gameOver.set(true);
                    System.out.println("Player " + playerNumber + " wins! Notifying other players...");
                    break;
                }

                Card discardCard = checkDiscard();
                drawAndDiscard(discardCard);
                Thread.sleep(1); // clueless

            } catch (InterruptedException e) {
                System.out.println("Player " + playerNumber + " was interrupted.");
                Thread.currentThread().interrupt(); // interupt if a problem occurs
                break;
            }
        }

        endSequence(); // output to files
        leftDeck.endSequence();
    }

    private void endSequence() {
        /*
         * create a file, and write our hand to it
         */
        ArrayList<Card> finalCards = hand.getHand();
        System.out.println("Player " + playerNumber + " has stopped. Outputting to file...");
        File outputFile = new File("Player " + playerNumber + " Output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(finalCards.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        /*
         * returns true if all cards in the hand are equal
         */
        ArrayList<Card> cards = hand.getHand();
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
