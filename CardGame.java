package src.main;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardGame {

    private List<Deck> decks;
    private List<Player> players;
    private int numPlayers;

    public static void main(String[] args)
    {
        CardGame game = new CardGame();
        game.start();
    }

    public void start() {
        //I dont believe this will have to be threaded 
        System.out.println("Hello! Welcome to the Software Development module coursework!");
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

        // Load and validate the pack
        while (!validPack) {
            System.out.println("Please enter the location of the pack to use:");
            String loadPack = scanner.nextLine();
            try (BufferedReader reader = new BufferedReader(new FileReader(loadPack))) {
                int lines = 0;
                while (reader.readLine() != null) lines++;

                if (lines == 8 * numPlayers) {
                    validPack = true;
                    // TODO: Add code to read the deck and distribute cards here (this will probably be threaded)
                    System.out.println("Pack is valid and contains the correct number of cards.");
                } else {
                    throw new WrongNumberOfCardsException();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please select a valid pack location.");
            } catch (IOException e) {
                System.out.println("Error reading the file. Please try again.");
            } catch (WrongNumberOfCardsException e) {
                System.out.println("The pack must contain exactly 8 * the number of players cards.");
            }
        }

        // Create and assign decks and players
        /** 
        createDecks();
        assignDecks();
        */
        //TODO: Start threads here
    }
    /** 
    private void createDecks() {
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck()); //Deck constructor might have to change
            //Deck will have to be threaded later as well, so we might want a function
            //DeckThreadStart(Deck deck)
        }
    }
     */
/** 
    private void assignDecks() {
        for (int i = 0; i < numPlayers; i++) {
            Deck leftDeck = decks.get(i);
            Deck rightDeck = decks.get((i + 1) % numPlayers); // Wraps around to the first deck
            players.add(new Player("Player " + (i + 1), leftDeck, rightDeck));
            //Players will be threaded so we will want a function PlayerThreadStart(Player player)
        }
    }
*/

    // Define a custom exception for the wrong number of cards
    private static class WrongNumberOfCardsException extends Exception {
        public WrongNumberOfCardsException() {
            super("Incorrect number of cards in the deck file.");
        }
    }
}