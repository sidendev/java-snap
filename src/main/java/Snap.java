import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.IOException;

public class Snap extends CardGame {
    private static final int SNAP_TIMEOUT_MS = 2000;
    private static final int DECK_SIZE = 52;
    private ArrayList<Card> dealtCards; // should not be final
    private Scanner scanner;
    private int cardsDealtCount;
    private final Player player1;
    private final Player player2;
    private boolean isPlayer1Turn;
    private final Object scannerThreadLock = new Object(); // needed for synchronized to block other thread
    private String player1Name;
    private String player2Name;

    public Snap() {
        super("Snap");
        this.dealtCards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.cardsDealtCount = 0;
        playerNameSetUp();
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.isPlayer1Turn = true;
    }

    // for junit testing:
    public Snap(boolean testMode) {
        super("Snap");
        this.dealtCards = new ArrayList<>();
        this.cardsDealtCount = 0;
        if (testMode) {
            this.player1Name = "TestPlayer1";
            this.player2Name = "TestPlayer2";
            this.player1 = new Player(player1Name);
            this.player2 = new Player(player2Name);
        } else {
            this.scanner = new Scanner(System.in);
            playerNameSetUp();
            this.player1 = new Player(player1Name);
            this.player2 = new Player(player2Name);
        }
        this.isPlayer1Turn = true;
    }

    public ArrayList<Card> getDeckOfCards() {
        return deckOfCards;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public int getCardsDealtCount() {
        return cardsDealtCount;
    }

    private void playerNameSetUp() {
        System.out.println();
        System.out.println("Welcome to Java Snap!");
        System.out.println();
        System.out.println("Players, lets get ready.");
        player1Name = setValidPlayerName(1);
        player2Name = setValidPlayerName(2);
    }

    private String setValidPlayerName(int playerNumber) {
        String name;
        while (true) {
            System.out.print("Enter Player " + playerNumber + " name: ");
            name = scanner.nextLine();

            try {
                if (name == null || name.trim().isEmpty()) {
                    System.out.println("Error: Name cannot be empty. Please try again.");
                    continue;
                }
                if (playerNumber == 2 && name.trim().equalsIgnoreCase(player1Name)) {
                    System.out.println("Error: Players cannot have the same name. Please choose a different name.");
                    continue;
                }
                return name.trim();
            } catch (Exception e) {
                System.out.println("Error reading input. Please try again. Exception: " + e.getMessage());
            }
        }
    }

    public void resetGame() {
        shuffleDeck();
        dealtCards.clear();
        cardsDealtCount = 0;
        playGame();
    }

    private void askToPlayAgain() {
        while (true) {
            System.out.println("Would you like to play again? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                resetGame();
                break;
            } else if (response.equalsIgnoreCase("N")) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please enter either Y or N.");
            }
        }
    }

    private void checkIfEntirePackDealt() {
        if (cardsDealtCount == DECK_SIZE) {
            System.out.println("Wow, this game is going on for a long time!");
            System.out.println("The dealer has now shuffled the deck.");
            shuffleDeck();
            dealtCards.clear();
            cardsDealtCount = 0;
        }
    }

    private void gameIntroMessage() {
        System.out.println();
        System.out.println("How to play Java Snap:");
        System.out.println("- Players take turns pressing Enter to be dealt a card.");
        System.out.println("- If two consecutive cards match, you must type 'snap' within 2 seconds!");
        System.out.println("- If you type 'snap' in time, you win!");
        System.out.println("- If you don't type 'snap' in time, you lose and the other player gets a point.");
        System.out.println("Press Enter to begin.");
        System.out.println();
        System.out.println("Current scores are, " + player1.getName() + ": " + player1.getScore()
                + " and " + player2.getName() + ": " + player2.getScore());
        System.out.println();
    }

    private Thread createInputThread(String[] userInput, boolean[] playerHasSnapped) {
        Thread inputThread = new Thread(() -> {
            try {
                userInput[0] = scanner.nextLine().trim();
                playerHasSnapped[0] = userInput[0].equalsIgnoreCase("snap"); // checking input matches
                synchronized (scannerThreadLock) {
                    scannerThreadLock.notify(); // notify scannerThreadLock if snap written - notify will stop it
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                System.err.println(e.getMessage()); // catch any error on input
            }
        });
        inputThread.start(); // starting the thread after set up
        return inputThread;
    }

    public void playGame() {
        gameIntroMessage();

        // Infinite game loop - exits with System.exit() when player chooses to quit game.
        // Will get warning on (while) in IDE
        while (true) {
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println(currentPlayer.getName() + "'s turn. Press Enter to get a card...");

            scanner.nextLine();

            Card newCard = dealCard();
            System.out.println("Card dealt: " + newCard);
            cardsDealtCount++;
            System.out.println();

            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
                playerWritesSnap();
            }

            dealtCards.add(newCard);

            checkIfEntirePackDealt();

            isPlayer1Turn = !isPlayer1Turn;
        }
    } // end playGame

    private void playerWritesSnap() {
        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
        final boolean[] playerHasSnapped = {false};
        final String[] userInput = {""};
        
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        Player nonCurrentPlayer = isPlayer1Turn ? player2 : player1;

        // creating input thread and starting
        Thread inputThread = createInputThread(userInput, playerHasSnapped);

        // waiting for 2 seconds or until input is received
        // this try catch should block other thread from attempting to access scanner during this time block
        try {
            synchronized (scannerThreadLock) {
                scannerThreadLock.wait(SNAP_TIMEOUT_MS);
            }
        } catch (InterruptedException e) {
            System.err.println("InterruptedException while waiting for input: " + e.getMessage());
        }

        // if thread is still alive after 2 seconds or no input received
        if (inputThread.isAlive() || !playerHasSnapped[0]) {
            System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
            System.out.println("Press Enter to continue...");
            nonCurrentPlayer.incrementScore();
            inputThread.interrupt(); // interrupts the thread

            scanner = new Scanner(System.in); // setting up a new scanner for next input - get error if this is not in place

            // clearing input buffer, IDE warning on (read) can be ignored
            try {
                while(System.in.available() > 0) {
                    System.in.read();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
            currentPlayer.incrementScore();
            askToPlayAgain();
            return;
        }

        askToPlayAgain();
    } // end playerWritesSnap

    public static void main(String[] args) {

        Snap snapGame = new Snap();
        snapGame.playGame();

    } // end Snap main method

} // end Snap class
