


public class Card 
{
    private int cardValue;
    
    public Card(int cardValue)
    {
        this.cardValue = cardValue;
    }

    public int getValue()
    {
        return cardValue;
    }

    public boolean allEqual(Card card) {
        /*
         * This is a method to check if 2 cards are equal
         * not strictly required, but in the context of sotware
         * dev, if we wanted to scale this up we would need this
         */
        if (this == card) return true; //returns true if this object is equal to the input object
        if (card == null || getClass() != card.getClass()) return false; //check that both the class and object are the same type
        Card myClass = (Card) card;
        return cardValue == myClass.cardValue;
    }
}