import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.IOException;

public class Snap extends CardGame {
    private ArrayList<Card> dealtCards; // should not be final
    private Scanner scanner;
    private int cardsDealtCount;
    private final Player player1;
    private final Player player2;
    private boolean isPlayer1Turn;
    private final Object scannerLock = new Object(); // needed for synchronized

    public Snap() {
        super("Snap");
        this.dealtCards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.cardsDealtCount = 0;

        System.out.println();
        System.out.println("Welcome to Java Snap!");
        System.out.println();
        System.out.println("Players, lets get ready.");
        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter Player 2 name: ");
        String name2 = scanner.nextLine();

        this.player1 = new Player(name1);
        this.player2 = new Player(name2);
        this.isPlayer1Turn = true;
    }

    private void resetGame() {
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
        if (cardsDealtCount == 52) {
            System.out.println("Wow, this game is going on for a long time!");
            System.out.println("The dealer has now shuffled the deck.");
            shuffleDeck();
            dealtCards.clear();
            cardsDealtCount = 0;
        }
    }

    public void playGame() {

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

        // Infinite game loop - exits with System.exit() when player chooses to quit game.
        // Will get warning on while in IDE
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

            // switching players
            isPlayer1Turn = !isPlayer1Turn;
        }
    } // playGame end

    // checking if player writes snap or not and handling outcome
    private void playerWritesSnap() {
        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
        final boolean[] playerHasSnapped = {false};
        final String[] userInput = {""};
        
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        Player nonCurrentPlayer = isPlayer1Turn ? player2 : player1;

        // creating input thread and starting
        Thread inputThread = createInputThread(userInput, playerHasSnapped);

        // waiting for 2 seconds or until input is received - this should stop the error for the thread
        // should stop multiple threads trying to access scanner
        try {
            synchronized (scannerLock) {
                scannerLock.wait(2000);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        // if thread is still alive after 2 seconds or no input received
        if (inputThread.isAlive() || !playerHasSnapped[0]) {
            System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
            System.out.println("Press Enter to continue...");
            nonCurrentPlayer.incrementScore();
            inputThread.interrupt();

            scanner = new Scanner(System.in); // setting up a new scanner - should fix error

            // clearing input buffer, warning on read can be ignored
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

    private Thread createInputThread(String[] userInput, boolean[] playerHasSnapped) {
        Thread inputThread = new Thread(() -> {
            try {
                userInput[0] = scanner.nextLine().trim();
                playerHasSnapped[0] = userInput[0].equalsIgnoreCase("snap");
                synchronized (scannerLock) {
                    scannerLock.notify();
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                System.err.println(e.getMessage()); // make more specific - testing
            }
        });

        inputThread.start();
        return inputThread;
    }

    public static void main(String[] args) {

        Snap snapGame = new Snap();
        snapGame.playGame();

    } // end snap main

} // end class
