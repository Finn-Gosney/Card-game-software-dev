import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardGame
{
    /**
     * The main thread of the project, 
     * User can state how many players there are
     * and what deck to use
     */

    private static List<Deck> decks = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();
    private static int numPlayers; 

    public static void main(String[] args)
    {
        System.out.println("Hello! Welcome to the Software Development module coursework!");
        Boolean validPlayerNumber = false;
        Boolean validPack = false;
        Scanner scanner = new Scanner(System.in); 
        while (!validPlayerNumber)
        { 
            try {
                System.out.println("Please enter how many players are playing");
                numPlayers = Integer.parseInt(scanner.nextLine()); 
                validPlayerNumber = true;
            } catch (Exception e) {
                System.out.println("Please enter a number");
            }
        }
        while (!validPack) {
            System.out.println("Please enter the location of the pack to use");
            String loadPack = scanner.nextLine();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(loadPack));
                int lines = 0;
                while (reader.readLine() != null) lines++;
                if (lines == 8 * numPlayers) {
                    // Do something with deck and cards here
                    reader.close();
                    validPack = true; // Set validPack to true if the pack is valid
                } else {
                    throw new WrongNumberOfCardsException();
                }
            } catch (FileNotFoundException | WrongNumberOfCardsException e) {
                System.out.println("Please select a valid pack location, and ensure the pack has 8 * the number of players cards in it");
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
        }
        createDecks();
        assignDecks();
        // We probably want to n new player classes, where n is the numPlayers
        // while simultaneously instantiating the deck classes 
        // Then we are going to have to distribute the cards from the pack
        // After that we want to start the threads for the program to run
    }

    private static void createDecks() {
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck());
        }
    }

    private static void assignDecks() { 
        for (int i = 0; i < numPlayers; i++) {
            Deck leftDeck = decks.get(i);
            Deck rightDeck = decks.get((i + 1) % numPlayers);  // Wraps around to the first deck
            players.add(new Player("Player " + (i + 1), leftDeck, rightDeck));
        }
    }
}

class WrongNumberOfCardsException extends Exception {
    public WrongNumberOfCardsException() {
        super("The number of cards in the pack is incorrect.");
    }
}

// Dummy Deck and Player classes for completeness
class Deck {
    // Deck implementation
}

class Player {
    public Player(String name, Deck leftDeck, Deck rightDeck) {
        // Player implementation
    }
}