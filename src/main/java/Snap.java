import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Snap extends CardGame {
    private ArrayList<Card> dealtCards;
    private Scanner scanner;
    private int cardsDealtCount;
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn;

    // Constructor
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

    private void restartGame() {
        System.out.println("Lets Play Again... press Enter to start!");

        // wait for user to press enter - catch any exceptions here
        try {
            System.in.read();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // make this a method - reset game
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
        System.out.println("- If you don't type 'snap' in time, you lose.");
        System.out.println("Press Enter to begin.");
        System.out.println();
        System.out.println("Current scores are, " + player1.getName() + ':' + player1.getScore()
                + " and " + player2.getName() + ':' + player2.getScore());
        System.out.println();

        // loop here of every card dealt and checking if winner - will keep looping through the deckOfCards
        while (true) {
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            Player otherPlayer = isPlayer1Turn ? player2 : player1;
            System.out.println(currentPlayer.getName() + "'s turn. Press Enter to get a card...");

            scanner.nextLine(); // wait for Enter press

            Card newCard = dealCard();
            System.out.println("Card dealt: " + newCard);
            cardsDealtCount++;
            System.out.println();

            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
                System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");

                if (playerWritesSnap()) {
                    System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
                    currentPlayer.incrementScore();
                    restartGame();
                } else {
                    System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
                    otherPlayer.incrementScore();
                    // askToPlayAgain();

                    restartGame();
                }
            }

            dealtCards.add(newCard); // storing the card for comparison

            // checking if 52 cards have been dealt
            if (cardsDealtCount == 52) {
                System.out.println("Wow, this game is going on for a long time!");
                System.out.println("The dealer has now shuffled the deck. Press Enter to continue...");

                shuffleDeck(); // shuffle the deck
                dealtCards.clear(); // resetting the list of dealt cards
                cardsDealtCount = 0; // resetting the counter
            }

            // switching players
            isPlayer1Turn = !isPlayer1Turn;

        }
    } // playGame end

    // running timer - set up playerWritesSnap - checking if they type snap in time - we get boolean result
    // default is false
    private boolean playerWritesSnap() {
        final boolean[] snapped = {false};
        Timer timer = new Timer(); // using Timer object - java util
        Thread inputThread = new Thread(() -> {
            try {
                if (scanner.hasNextLine()) { // checking before reading input
                    String input = scanner.nextLine().trim();
                    if (input.equalsIgnoreCase("snap")) {
                        snapped[0] = true;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error reading input: " + e.getMessage()); // checking any error
            }
        });

        inputThread.start(); // starting the thread

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inputThread.interrupt();
            }
        }, 2000); // 2 seconds timeout - can change this

        try {
            inputThread.join(2000); // holds up main thread for 2 seconds
        } catch (Exception e) { // this section to be fixed - testing - checking for any exception  for now
            System.err.println(e.getMessage());
        }

        timer.cancel(); // stopping the timer

        return snapped[0]; // if true returned player wrote snap in time, if false they did not

    } // end playerWritesSnap


    // to be fixed - keep it simple
    private void askToPlayAgain() {
        System.out.println("Would you like to play again? (Y/N)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("Y")) {
            restartGame();
        } else {
            System.out.println("Thanks for playing!");
            scanner.close();
        }


//        System.out.println("Would you like to play again? (Y/N)");
//
//        // clear up scanner - checking
//        while (scanner.hasNextLine()) {
//            scanner.nextLine();
//        }
//        while (true) {
//            String response = scanner.nextLine().trim();
//            if (response.equalsIgnoreCase("Y")) {
//                restartGame();
//                return;
//            } else if (response.equalsIgnoreCase("N")) {
//                System.out.println("Thanks for playing!");
//                scanner.close();
//                System.exit(0);
//            } else {
//                System.out.println("Invalid input. Please type 'Y' or 'N'.");
//            }
//        }

    } // end askToPlayAgain


    public static void main(String[] args) {

        // when this file is run the game should start, a fresh new CardGame - deckOfCards should get created.
        Snap snapGame = new Snap();
        snapGame.playGame();



    } // snap main



} // end class
