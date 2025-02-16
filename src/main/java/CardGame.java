import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    private String name; // name of type of game - Snap
    public ArrayList<Card> deckOfCards;

    // fields needed to set up deck of cards: TO BE PUT INTO ENUMS - also put into the ENUMS the SUIT ORDER
//    private static final String[] SUITS = {"♥", "♣", "♦", "♠"};
//    private static final String[] SYMBOLS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
//    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

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
        deckOfCards.add(cardToDeal); // putting it at the bottom
        return cardToDeal;
    }

    public void sortDeckInNumberOrder() {
        Collections.sort(deckOfCards);
    }

    public void sortDeckIntoSuits() {
        deckOfCards.sort(new SortingBySuit());
    }

    public static void main(String[] args) {
        // This will be the actual Snap game (Main) when set up
        System.out.println("Let's Play Snap!");

        // to test printing out all 52 shuffled cards
        CardGame game = new CardGame("Snap"); // starting a new game

        game.getDeck(); // listing out all cards - check it is shuffled

        System.out.println("This is the first card: " + game.dealCard());
        System.out.println("This is the next card: " + game.dealCard());
        System.out.println("This is the next card: " + game.dealCard());
        System.out.println("This is the next card: " + game.dealCard());
        System.out.println("This is the next card: " + game.dealCard());

        game.sortDeckInNumberOrder(); // should sort in number order 2 to Ace
        System.out.println("This is the deck in number sorted order: ");
        game.getDeck();

        game.sortDeckIntoSuits(); // sorts the deck into suits
        System.out.println("This is the deck now sorted by suit: ");
        game.getDeck();



    } // end main


} // end class
