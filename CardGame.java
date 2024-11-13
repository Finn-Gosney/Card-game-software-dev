
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class CardGame {

    private List<Deck> decks;
    private List<Player> players;
    private List<Hand> hands;
    private int numPlayers;
    private List<Card> cards;
    private CardManager cardManager = new CardManager();

    public static void main(String[] args)
    {
        CardGame game = new CardGame();
        game.start();
    }

    public void start() {
        //I dont believe this will have to be threaded 
        System.out.println("Hello! Welcome to the Software Development module coursework!");
        numPlayers = getPlayerNumber();
        cards = getValidPack();
       
        // Create and assign decks and players
        createDecks(cards, numPlayers);
        assignDecks(cards, numPlayers);

        //TODO: Start threads here
    }
    
    private int getPlayerNumber()
    {
        
        boolean validPlayerNumber = false;
        boolean validPack = false;
        Scanner scanner = new Scanner(System.in);

        // Get number of players
        while (!validPlayerNumber) {
            try {
                System.out.println("Please enter how many players are playing:");
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers < 2) {
                    System.out.println("Please enter a number greater than 1.");
                } else {
                    validPlayerNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return numPlayers;
    }

    private List<Card> getValidPack(){
    /*
     * Get a valid pack from the user to play with 
     */
    Boolean validPack = false;
    String loadPack = "";
    Scanner scanner = new Scanner(System.in);
     while (!validPack) {
            System.out.println("Please enter the location of the pack to use:");
            loadPack = scanner.nextLine();
            try (BufferedReader reader = new BufferedReader(new FileReader(loadPack))) {
                int lines = 0;
                while (reader.readLine() != null) lines++;

                if (lines == 8 * numPlayers) {
                    System.out.println("Pack is valid and contains the correct number of cards.");
                    validPack = true;
                } else {
                    throw new WrongNumberOfCardsException();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please select a valid pack location.");
            } catch (IOException e) {
                System.out.println("Error reading the file. Please try again.");
            } catch (WrongNumberOfCardsException e) {
                System.out.println(e.getMessage());
            }
        }
        cards = getCards(loadPack);
        return cards;
    }

    private List<Card> getCards(String loadPack)
    {
        /**
         * Extract cards from the pack
         */
        File pack = new File(loadPack + ".txt");
        int[] allCardNumbers = new int[8 * numPlayers];
        Scanner reader;
        try {
            reader = new Scanner(pack);
            List<Integer> cardNumbers = new ArrayList<>();
        int count = 0;
        
        while (reader.hasNextLine()) {
            int cardNumber = Integer.parseInt(reader.nextLine());
            allCardNumbers[count] = cardNumber;
            count++;
        }
        } 
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
            cards = cardManager.generateCards(allCardNumbers);
        
        return cards;
    }

    private void createDecks(List<Card> cards, int numPlayers) {
        /*
         * We create the decks that the players will use before the players
         * so we have to make sure they start empty and then fill them later
         * with the rest of the pack
         */
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck(i)); //This is a list of all our decks 

            //Deck will have to be threaded later as well, so we might want a function
            //DeckThreadStart(Deck deck)
        }
    }

    private void assignDecks(List<Card> cards, int numPlayers) {
        /*
         * This method assigns decks to players by calling the player constructor with
         * the correct deck as the left deck and the correct deck as the right deck
         * it also then calls a function to distribute the remaining cards to the decks
         * 
         * This method is definitely complicated, please ask if you dont understand
         */ 
        int cardsToHands = 4*numPlayers; //We want to distribute the first 4*n cards to players hands
        List<Card> firstCards = new ArrayList<>(cards.subList(0, cardsToHands));
        cards.subList(0, cardsToHands).clear();  //We can remove the cards that have been distributed so
                                             //that we can distribute to decks 

         for (int i = 0; i < numPlayers; i++) {
            List<Card> playerHandCards = getEveryFourthCard(firstCards, i); // Select every 4th card starting from index 'i'
            hands.add(new Hand(playerHandCards, i+1)); // Add a hand for each player
            }

        for (int i = 0; i < numPlayers; i++) {
            Deck leftDeck = decks.get(i);
            Deck rightDeck = decks.get((i + 1) % numPlayers); // Wraps around to the first deck
            players.add(new Player(i + 1, leftDeck, rightDeck)); // Initialize players with hands
            startPlayerThread(players.get(i));
        }
        deckDistribution(decks, cards);
    }

    private void deckDistribution(List<Deck> decks, List<Card> cards)
    {
       /*
       Distribute to decks before starting the player threads
        */ 
        
        for(int i = 0; i < numPlayers; i++)
        {
            List<Card> deckCards = getEveryFourthCard(cards, i);
            decks.get(i).addCards(deckCards);
        }
    }

    private List<Card> getEveryFourthCard(List<Card> cards, int startIndex) {
        return IntStream.range(0, cards.size())
                .filter(n -> (n - startIndex) % 4 == 0) // Adjust the starting index for each player
                .mapToObj(cards::get)
                .toList();
    }

    private void startPlayerThread(Player player) {
        // Create a new thread for the player and start it
        new Thread(() -> player.startPlayerThread()).start();
    }

    // Define a custom exception for the wrong number of cards
    private static class WrongNumberOfCardsException extends Exception {
        public WrongNumberOfCardsException() {
            super("Incorrect number of cards in the deck file.");
        }
    }
}