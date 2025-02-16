import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Snap extends CardGame {
    private ArrayList<Card> dealtCards;
    private Scanner scanner;
    private int cardsDealtCount;
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn;

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

        // Snap game loop
        while (true) {
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println(currentPlayer.getName() + "'s turn. Press Enter to get a card...");

            scanner.nextLine(); // wait for Enter press

            Card newCard = dealCard();
            System.out.println("Card dealt: " + newCard);
            cardsDealtCount++;
            System.out.println();

            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
                playerWritesSnap(); // handles if winner or loser for this round - checking if written snap
            }

            dealtCards.add(newCard); // storing the card for comparison

            // checking if 52 cards have been dealt - make this a method
            if (cardsDealtCount == 52) {
                System.out.println("Wow, this game is going on for a long time!");
                System.out.println("The dealer has now shuffled the deck.");

                shuffleDeck(); // shuffle the deck
                dealtCards.clear(); // resetting the list of dealt cards
                cardsDealtCount = 0; // resetting the counter
            }

            // switching players
            isPlayer1Turn = !isPlayer1Turn;
        }
    } // playGame end

    // checking if player writes snap or not and handling outcome
    private boolean playerWritesSnap() {
        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
        final boolean[] snapped = {false}; // check on this - testing
        final String[] userInput = {""}; // check on this - testing
        
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        Player nonCurrentPlayer = isPlayer1Turn ? player2 : player1;

        // making a separate thread for input
        Thread inputThread = new Thread(() -> {
            try {
                userInput[0] = scanner.nextLine().trim();
                snapped[0] = true;
                synchronized (scanner) {
                    scanner.notify();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage()); // make more specific - testing
            }
        });

        // starting the input thread
        inputThread.start();

        // waiting for 2 seconds or until input is received - this should stop the error for the thread
        // should stop multiple threads trying to access scanner
        try {
            synchronized (scanner) {
                scanner.wait(8000);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        // if thread is still alive after 2 seconds or no input received
        if (inputThread.isAlive() || !snapped[0]) {
            System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
            System.out.println("Press Enter to continue...");
            nonCurrentPlayer.incrementScore();
            inputThread.interrupt();

            scanner = new Scanner(System.in); // setting up a new scanner - should fix error

            // should clear input buffer
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

            while (true) {
                System.out.println("Would you like to play again? (Y/N)"); // make this a method --------
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

            return true;
        }

        while (true) {
            System.out.println("Would you like to play again? (Y/N)"); // make this a method --------
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

        return false;
    } // end playerWritesSnap
    
    public static void main(String[] args) {

        Snap snapGame = new Snap();
        snapGame.playGame();

    } // end snap main

} // end class
