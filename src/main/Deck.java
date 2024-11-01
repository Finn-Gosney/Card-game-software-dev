package src.main;
import java.util.Scanner;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

public class Deck
{
    private Card[] cards;
    private Lock deckLock;
}