
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

public class CardGame {

    private List<Deck> decks = new ArrayList<Deck>();
    private List<Player> players = new ArrayList<Player>();
    private List<Hand> hands = new ArrayList<Hand>();
    private int numPlayers;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private CardManager cardManager = new CardManager();

    public static void main(String[] args) {
        CardGame game = new CardGame();
        game.start();
    }

    public void start() {
       /*
       *entry point of CardGame
        */ 
        System.out.println("Hello! Welcome to the Software Development module coursework!");
        numPlayers = getPlayerNumber();
        cards = getValidPack(numPlayers);

        // Create and assign decks and players
        createDecks(cards, numPlayers);
        assignDecks(cards, numPlayers);

    }

    private int getPlayerNumber() {
        /*
         * Gets the number of players playing the game
         */
        boolean validPlayerNumber = false;
        Scanner scanner = new Scanner(System.in);

        // Get number of players
        while (!validPlayerNumber) {
            try {
                System.out.println("Please enter how many players are playing:");
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers < 1) {
                    System.out.println("Please enter a number greater than 0.");
                } else {
                    validPlayerNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return numPlayers;
    }

    public ArrayList<Card> getValidPack(int numPlayers) {
        /*
         * Recieves user input and ensures it is correct,
         * then loads the pack
         */
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> cards = null;
        boolean validPack = false;
        String loadPack = "";

        try {
            while (!validPack) {
                System.out.println("Please enter the location of the pack to use:");
                loadPack = scanner.nextLine();
                System.out.println("Attempting to load pack from: " + loadPack);

                File pack = new File("src/main/resources/" + loadPack);
                // Now read the file from the InputStream
                try (BufferedReader reader = new BufferedReader(new FileReader(pack))) {
                    int lines = 0;
                    while (reader.readLine() != null)
                        lines++; // Clean one line solution to count number of cards

                    // Validate the pack size
                    if (lines == 8 * numPlayers) {
                        System.out.println("Pack is valid and contains the correct number of cards.");
                        validPack = true;
                    } else {
                        throw new WrongNumberOfCardsException("The pack does not contain the correct number of cards.");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading the file. Please try again.");
                } catch (WrongNumberOfCardsException e) {
                    System.out.println(e.getMessage());
                }

            }

            // After the pack is validated, load the cards
            cards = getCards(loadPack);

        } finally {
            scanner.close();
        }

        return cards;
    }

    private ArrayList<Card> getCards(String loadPack) {
        /**
         * Extract cards from the pack
         */
        File pack = new File("src/main/resources/" + loadPack);
        int[] allCardNumbers = new int[8 * numPlayers];
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(pack));
            int count = 0;

            while ((line = reader.readLine()) != null) {
                int cardNumber = Integer.parseInt(line);
                allCardNumbers[count] = cardNumber;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cards = cardManager.generateCards(allCardNumbers);

        return cards;
    }

    private void createDecks(ArrayList<Card> cards, int numPlayers) {
        /*
         * We create the decks that the players will use before the players
         * so we have to make sure they start empty and then fill them later
         * with the rest of the pack
         */
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck(i)); // This is a list of all our decks

        }
    }

    private void assignDecks(ArrayList<Card> cards, int numPlayers) {
        /*
         * This method assigns decks to players 
         * then calls a function to distribute the remaining cards to the decks 
         */
        int cardsToHands = 4 * numPlayers; // We want to distribute the first 4*n cards to players hands
        ArrayList<Card> firstCards = new ArrayList<>(cards.subList(0, cardsToHands));
        cards.subList(0, cardsToHands).clear(); // We can remove the cards that have been distributed so
        // that we can distribute to decks

        for (int i = 0; i < numPlayers; i++) {
            ArrayList<Card> playerHandCards = getEveryNthCard(firstCards, i); // Select every 4th card starting from
                                                                              // index 'i'
            hands.add(new Hand(playerHandCards, i + 1)); // Add a hand for each player
        }

        for (int i = 0; i < numPlayers; i++) {
            Deck leftDeck = decks.get(i);
            Deck rightDeck = decks.get((i + 1) % numPlayers); // Wraps around to the first deck
            players.add(new Player(i + 1, leftDeck, rightDeck)); // Initialize players with hands
            startPlayerThread(players.get(i));
        }
        deckDistribution(decks, cards);
    }

    private void deckDistribution(List<Deck> decks, ArrayList<Card> cards) {
        /*
         * Distribute to decks before starting the player threads
         */

        for (int i = 0; i < numPlayers; i++) {
            ArrayList<Card> deckCards = getEveryNthCard(cards, i);
            decks.get(i).addCards(deckCards);
        }
    }

    private ArrayList<Card> getEveryNthCard(ArrayList<Card> cards, int startIndex) {
        /*
         * Helper function for round robin distribution
         */
        ArrayList<Card> result = new ArrayList<>();
        for (int i = startIndex; i < cards.size(); i += numPlayers) {
            result.add(cards.get(i));
        }
        return result;
    }

    private void startPlayerThread(Player player) {
        /*
         * Set off all player threads as they are created
         */
        player.setHand(hands);

        Thread thread = new Thread(player);
        thread.start();
    }

    // Define a custom exception for the wrong number of cards
    private static class WrongNumberOfCardsException extends Exception {
        public WrongNumberOfCardsException(String message) {
            super("Incorrect number of cards in the deck file.");
        }
    }
}