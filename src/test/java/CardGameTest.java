import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardGameTest {

    public CardGame cardGame;

    @BeforeEach
    public void setUp() {
        cardGame = new CardGame("Snap");
    }

    @Test
    @DisplayName("CardGame Constructor initializes deck with 52 cards correctly") // partial check of setDeckOfCards
    void constructor_NewCardGame_DeckHas52Cards() {
        assertEquals(52, cardGame.deckOfCards.size());
    }

    @Test
    @DisplayName("getDeck prints 52 cards")
    void getDeck_CalledGetDeck_Prints52Lines() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // to capture System.out
        System.setOut(new PrintStream(outputStream)); // to set up outputStream to test System.out
        cardGame.getDeck();
        System.setOut(System.out);
        long lineCount = outputStream.toString().lines().count();
        assertEquals(52, lineCount);
    }

    @Test
    @DisplayName("shuffleDeck shuffles the cards correctly")
    void shuffleDeck_ShuffleCardsAfterCopy_OrderChangesFromOriginalDeck() {
        ArrayList<Card> originalDeck = new ArrayList<>(cardGame.deckOfCards);
        cardGame.shuffleDeck();
        assertNotEquals(originalDeck, cardGame.deckOfCards);
    }

    @Test
    @DisplayName("dealCard moves the top card to the bottom of the deck")
    void dealCard_CalledDealCard_TopCardChangesAfterwards() {
        Card originalTopCard = cardGame.deckOfCards.getFirst();
        cardGame.dealCard();
        Card newTopCard = cardGame.deckOfCards.getFirst();
        assertNotEquals(originalTopCard, newTopCard);
    }

    @Test
    @DisplayName("sortDeckInNumberOrder sorts the deck by card value")
    void sortDeckInNumberOrder_DeckSorted_LowestValueToHighestValue() {
        cardGame.shuffleDeck(); // to confirm deck is in random order
        cardGame.sortDeckInNumberOrder();
        int firstCardValue = cardGame.deckOfCards.getFirst().getValue();
        int lastCardValue = cardGame.deckOfCards.getLast().getValue();
        assertEquals(2, firstCardValue);
        assertEquals(14, lastCardValue);
    }

    @Test
    @DisplayName("sortDeckIntoSuits sorts the deck by suit order (♥ first, ♠ last)")
    void sortDeckIntoSuits_DeckSorted_CorrectSuitOrderMatchingEnum() {
        cardGame.shuffleDeck();
        cardGame.sortDeckIntoSuits();
        String firstCardSuit = cardGame.deckOfCards.getFirst().getSuit(); // Should be ♥
        String lastCardSuit = cardGame.deckOfCards.getLast().getSuit(); // Should be ♠
        assertEquals("♥", firstCardSuit, "First card suit should be ♥ (Hearts)");
        assertEquals("♠", lastCardSuit, "Last card suit should be ♠ (Spades)");
    }
}
