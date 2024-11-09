package src.main;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardGame {

    private List<Deck> decks;
    private List<Player> players;
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

    private List<Card> getValidPack() {
        boolean validPack = false;

    /*
     * Get a valid pack from the user
     */

     while (!validPack) {
            System.out.println("Please enter the location of the pack to use:");
            Scanner scanner = new Scanner(System.in);
            String loadPack = scanner.nextLine();
            try (BufferedReader reader = new BufferedReader(new FileReader(loadPack))) {
                int lines = 0;
                while (reader.readLine() != null) lines++;

                if (lines == 8 * numPlayers) {
                    System.out.println("Pack is valid and contains the correct number of cards.");
                    validPack = true;
                    List<Card> cards = getCards(loadPack);
                } else {
                    throw new WrongNumberOfCardsException();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please select a valid pack location.");
            } catch (IOException e) {
                System.out.println("Error reading the file. Please try again.");
            } catch (WrongNumberOfCardsException e) {
                System.out.println("Rip bozo");
            }
        }
        return cards;
    }

    private List<Card> getCards(String loadPack)
    {
        /**
         * Extract cards from the pack
         */
        File pack = new File(loadPack + ".txt");
        Scanner reader = new Scanner(pack);
        while (reader.hasNextLine()) {
            //not done properly
            int[] cardNumbers = Integer.parseInt(reader.nextLine());
            List<Card> cards = cardManager.generateCards(cardNumbers);
        }
        return cards;
    }

    private void createDecks(List<Card> cards, int numPlayers) {
        /*
         * We create the decks that the players will use before the players
         * so we have to make sure they start empty and then fill them later
         * with the rest of the pack
         */
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck()); //This is a list of all our decks 

            //Deck will have to be threaded later as well, so we might want a function
            //DeckThreadStart(Deck deck)
        }
    }

    private void assignDecks(List<Card> cards, int numPlayers) {
        /*
         * This method assigns decks to players by calling the player constructor with
         * the correct deck as the left deck and the correct deck as the right deck
         */ 

         //TODO:
         //I need to fix the problem that we need to distribute to the hands first
         //and then to the decks 
         //the catch is it has to be in a round robin fashion, requiring threading

        for (int i = 0; i < numPlayers; i++) {
            Deck leftDeck = decks.get(i);
            Deck rightDeck = decks.get((i + 1) % numPlayers); // Wraps around to the first deck
            players.add(new Player((i + 1), leftDeck, rightDeck, fourCards)); //This is where players are instantiated
            //Players will be threaded so we will want a function PlayerThreadStart(Player player)
        }
    }

    // Define a custom exception for the wrong number of cards
    private static class WrongNumberOfCardsException extends Exception {
        public WrongNumberOfCardsException() {
            super("Incorrect number of cards in the deck file.");
        }
    }
}