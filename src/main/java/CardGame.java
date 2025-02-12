import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    // fields:
    // name field - this is the name of the card
    private String name;

    // ArrayList<Card> for the deckOfCards that contains all 52 cards
    // the ArrayList should be created when the game is constructed
    private ArrayList<Card> deckOfCards;

    // fields needed to set up deck of cards:
    private static final String[] SUITS = {"♥", "♣", "♦", "♠"};
    private static final String[] SYMBOLS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    // constructor:
    // constructor sets up the name field
    public CardGame() {
        this.deckOfCards = new ArrayList<>();
        setDeckOfCards();
    }

    // methods:
    // method for creating the deck, setDeckOfCards - this will shuffle and set up the cards deckOfCards.
    public void setDeckOfCards() {
        deckOfCards.clear(); // This should clear previous deckOfCards ready for new set

        // setting up a full deck of 52 cards
        for (String suit : SUITS) {
            int index = 0;
            for (String symbol : SYMBOLS) {
                deckOfCards.add(new Card(suit, symbol, VALUES[index]));
                index++; // Increments through each index in VALUES
            }
        }

        Collections.shuffle(deckOfCards); // this will shuffle deckOfCards after creating array of all cards
    }

    // getDeck method that lists out the cards in the deck.
    public ArrayList<Card> getDeck() {
        return deckOfCards;
    }


    public static void main(String[] args) {
        // This will be the actual Snap game (Main) when set up
        System.out.println("Let's Play Snap!");

        CardGame game = new CardGame();
        for (Card card : game.getDeck()) {
            System.out.println(card);
        }



    } // end main




} // end class
