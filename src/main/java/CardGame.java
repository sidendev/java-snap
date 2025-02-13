import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    // fields:
    // name field - this is the name of the card game = example: "Snap"
    private String name;

    // ArrayList<Card> for the deckOfCards that contains all 52 cards
    // the ArrayList should be created when the game is constructed
    private ArrayList<Card> deckOfCards;

    // fields needed to set up deck of cards: TO BE PUT INTO ENUMS - also put into the ENUMS the SUIT ORDER
    private static final String[] SUITS = {"♥", "♣", "♦", "♠"};
    private static final String[] SYMBOLS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    // constructor:
    // constructor sets up the name field
    public CardGame(String name) {
        this.deckOfCards = new ArrayList<>(); // should we shuffle on new game start
        setDeckOfCards();
        this.name = name; // name of card game = Snap
    }

    // methods:
    // method for creating the deck, setDeckOfCards - this will shuffle and set up the cards deckOfCards.
    // setDeckOfCards needs to be separated out into methods, they get called on start up
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

        // Collections.shuffle(deckOfCards); // this will shuffle deckOfCards on initial game load - just call method
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(deckOfCards); // this will shuffle deckOfCards when called
    }

    // getDeck method that lists out the cards in the deck.
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

    // sorts deck in number order using the values of the card - not sure if has to return ArrayList<Card>
    public void sortDeckInNumberOrder() {
        Collections.sort(deckOfCards);
    }

    // sorts deck into suits, 2,3,4,5,6,7,8,9,10,J,Q,K,A of hearts etc - using a comparator for this
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

//        game.sortDeckInNumberOrder(); // should sort in number order 2 to Ace
//        System.out.println("This is the deck in number sorted order: ");
//        game.getDeck();

//        game.sortDeckIntoSuits(); // sorts the deck into suits
//        System.out.println("This is the deck now sorted by suit: ");
//        game.getDeck();



    } // end main




} // end class
