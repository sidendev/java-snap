import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class SnapTest {

    private Snap snapGame;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        snapGame = new Snap(true);
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Snap Constructor initializes deck with 52 cards correctly")
    void constructor_NewSnapGame_DeckHas52Cards() {
        assertEquals(52, snapGame.getDeckOfCards().size());
    }

    @Test
    @DisplayName("dealCard moves the top card to the bottom of the deck")
    void dealCard_CalledDealCard_TopCardChangesAfterwards() {
        Card originalTopCard = snapGame.deckOfCards.getFirst();
        snapGame.dealCard();
        Card newTopCard = snapGame.deckOfCards.getFirst();
        assertNotEquals(originalTopCard, newTopCard);
    }

    @Test
    @DisplayName("Player score increments correctly")
    void incrementScore_PlayerScores_ScoreIncreasedByOne() {
        Player player = snapGame.getPlayer1();
        int initialScore = player.getScore();
        player.incrementScore();
        assertEquals(initialScore + 1, player.getScore());
    }

    @Test
    @DisplayName("getPlayer1 returns correct player name")
    void getPlayer1_GetterCalled_ReturnsCorrectName() {
        assertEquals("TestPlayer1", snapGame.getPlayer1().getName());
    }

    @Test
    @DisplayName("getPlayer2 returns correct player name")
    void getPlayer2_GetterCalled_ReturnsCorrectName() {
        assertEquals("TestPlayer2", snapGame.getPlayer2().getName());
    }

    @Test
    @DisplayName("isPlayer1Turn is set to true on game initialization")
    void isPlayer1Turn_GameInitialized_ReturnsTrue() {
        assertTrue(snapGame.isPlayer1Turn());
    }

    // clean up after tests
    @AfterAll
    public static void tearDown() {
        System.setOut(originalOut);
        System.setIn(System.in);
    }
}

