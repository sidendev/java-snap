import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    public String name; // name of type of game - Snap
    public ArrayList<Card> deckOfCards;

    private static final CardSuit[] SUITS = CardSuit.values();
    private static final CardSymbol[] SYMBOLS = CardSymbol.values();
    private static final CardValue[] VALUES = CardValue.values();

    public CardGame(String name) {
        this.deckOfCards = new ArrayList<>();
        setDeckOfCards();
        this.name = name;
    }

    public void setDeckOfCards() {
        deckOfCards.clear(); // clear any previous deckOfCards ready for new set

        for (CardSuit suit : SUITS) {
            int index = 0;
            for (CardSymbol symbol : SYMBOLS) {
                deckOfCards.add(new Card(suit, symbol, VALUES[index]));
                index++;
            }
        }

        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(deckOfCards);
    }

    public void getDeck() {
        for (Card card : deckOfCards) {
            System.out.println(card);
        }
    }

    public Card dealCard() {
        Card cardToDeal = deckOfCards.removeFirst(); // removing the first card
        deckOfCards.add(cardToDeal); // putting card at the bottom of deck
        return cardToDeal;
    }

    public void sortDeckInNumberOrder() {
        Collections.sort(deckOfCards);
    }

    public void sortDeckIntoSuits() {
        deckOfCards.sort(new SortingBySuit());
    }

}
