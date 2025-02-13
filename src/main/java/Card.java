public class Card implements Comparable<Card> {
    // fields:
    // Heart: ♥ (U+2665)
    // Club: ♣ (U+2663)
    // Diamond: ♦ (U+2666)
    // Spade: ♠ (U+2660)
    // String suit. Use the unicode characters of heart, club, diamond and spades.
    private String suit;

    // String symbol (2,3,4,5,6,7,8,9,10,J,Q,K,A)
    private String symbol;

    // int value (2,3,4,5,6,7,8,9,10,11,12,13,14)
    private int value;

    // constructor:
    public Card(String suit, String symbol, int value) {
        this.suit = suit;
        this.symbol = symbol;
        this.value = value;
    }

    // setting up getters for the card - will need possibly going forward
    public String getSuit() {
        return suit;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    // methods:
    // a toString method that describes the class
    public String toString() {
        return symbol + " " + suit;
    }

    // so we can use Collections.sort and sort deck by value of card
    public int compareTo(Card otherCard) {
        return Integer.compare(this.value, otherCard.value);
    }

} // end class
