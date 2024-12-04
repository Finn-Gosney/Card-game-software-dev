# Card-game-software-dev
Second year project for my software development module


Overview

CardGame is a multithreaded card game simulation written in Java. The game is designed for multiple players who interact with decks and hands of cards in a concurrent environment. Players draw and discard cards with the goal of forming a winning hand, while the game ensures thread safety and correct synchronization of shared resources like decks.
Features

    Supports a dynamic number of players.
    Uses multithreading for concurrent player actions.
    Ensures thread safety during card operations using synchronized methods.
    Validates input pack files for correct card distribution.
    Outputs player hands and deck states to files upon game completion.

How It Works

    Setup Phase:
        Prompts the user to input the number of players.
        Validates the card pack to ensure it contains 8 * numPlayers cards.
        Distributes cards to player hands and decks.

    Gameplay Phase:
        Each player runs as a separate thread.
        Players draw a card from their left deck, discard a card to their right deck, and update their hand.
        Players continuously check if they have achieved a winning condition (all cards in their hand are the same).

    Endgame Phase:
        The first player to achieve the winning condition stops the game.
        Outputs each player's final hand and each deck's contents to respective files.

Dependencies

    Java 8+

Design Highlights

    Thread Safety:
        Deck operations (drawCard, discardCard) are synchronized to prevent race conditions.
    Scalability:
        The game dynamically adjusts to the number of players and validates pack sizes accordingly.
    Abstraction:
        Separate classes handle responsibilities for cards, decks, hands, and players, ensuring clean and maintainable code.

Usage

    Run the CardGame class to start the program.
    Follow the on-screen prompts:
        Enter the number of players.
        Provide the relative path to a valid card pack.
    The game will run until a player wins. Outputs will be saved to the project directory.