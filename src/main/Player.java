package src.main;
import java.util.Scanner;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
 
public class Player
{
    private int PlayerNumber;
    private Deck leftDeck;
    private Deck rightDeck;
    private boolean gameOver;
    private Lock playerLock;
    private Hand hand;

    public Player(int PlayerNumber, Deck leftDeck, Deck rightDeck) {
        this.PlayerNumber = PlayerNumber;
        this.hand = new Hand();
        this.playerLock = new ReentrantLock();
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }



    private class Hand 
    {
        private Card[] cards;
        private Lock handLock;
    }
}