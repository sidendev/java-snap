public class Card implements Comparable<Card> {
    private String suit;
    private String symbol;
    private int value;

    public Card(CardSuit suit, CardSymbol symbol, CardValue value) {
        this.suit = suit.getSuit();
        this.symbol = symbol.getSymbol();
        this.value = value.getValue();
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

//    public int compareTo(Card otherCard) {
//        int suitCompare = suit.compareTo(otherCard.suit);
//        if (suitCompare != 0) {
//            return suitCompare;
//        }
//        return value.compareTo(otherCard.value);
//    }
}
