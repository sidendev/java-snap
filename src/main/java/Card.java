public class Card implements Comparable<Card> {
    private String suit;
    private String symbol;
    private int value;

    public Card(String suit, String symbol, int value) {
        this.suit = suit;
        this.symbol = symbol;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return symbol + " " + suit;
    }

    public int compareTo(Card otherCard) {
        return Integer.compare(this.value, otherCard.value);
    }
}
