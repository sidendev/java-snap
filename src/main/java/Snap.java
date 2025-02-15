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
                if (playerWritesSnap()) {
                    System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
                    currentPlayer.incrementScore();

                    System.out.println("Would you like to play again? (Y/N)");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("Y")) {
                        restartGame();
                    } else {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                    }

                    // restartGame();
                } else {
                    // own method for this part?
                    System.out.println("Would you like to play again? (Y/N)");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("Y")) {
                        restartGame();
                    } else {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                    }
                }
            }

            dealtCards.add(newCard); // storing the card for comparison

            // checking if 52 cards have been dealt
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

    private boolean playerWritesSnap() {
        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
        final boolean[] snapped = {false};
        Player otherPlayer = isPlayer1Turn ? player2 : player1; // FIX THIS
        Player currentPlayer = isPlayer1Turn ? player1 : player2; // FIX THIS

        long startTime = System.currentTimeMillis();
        String input = scanner.nextLine().trim();
        long endTime = System.currentTimeMillis();

        if (endTime - startTime > 8000 || !input.equalsIgnoreCase("snap")) {
            System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
            otherPlayer.incrementScore();
            System.out.println("Would you like to play again? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                restartGame();
            } else {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        } else {
            snapped[0] = true;
            System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
            currentPlayer.incrementScore();
            System.out.println("Would you like to play again? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                restartGame();
            } else {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        }

        return snapped[0];
    }

    // WIP - testing
//    private boolean playerWritesSnap() {
//        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
//        final boolean[] snapped = {false};
//        Player otherPlayer = isPlayer1Turn ? player2 : player1; // should be non current player - FIX THIS
//        Player currentPlayer = isPlayer1Turn ? player1 : player2; // FIX THIS
//
//        long startTime = System.currentTimeMillis();
//        String input = scanner.nextLine().trim();
//        long endTime = System.currentTimeMillis();
//
//        if (endTime - startTime <= 2000 && input.equalsIgnoreCase("snap")) {
//            snapped[0] = true;
//            System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
//            currentPlayer.incrementScore();
//            System.out.println("Would you like to play again? (Y/N)");
//            String response = scanner.nextLine();
//            if (response.equalsIgnoreCase("Y")) {
//                restartGame();
//            } else {
//                System.out.println("Thanks for playing!");
//                System.exit(0);
//            }
//        } else {
//            System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
//            otherPlayer.incrementScore();
//            System.out.println("Would you like to play again? (Y/N)");
//            String response = scanner.nextLine();
//            if (response.equalsIgnoreCase("Y")) {
//                restartGame();
//            } else {
//                System.out.println("Thanks for playing!");
//                System.exit(0);
//            }
//        }
//
//        return snapped[0];
//    }

    // testing...........
//    private boolean playerWritesSnap() {
//        System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
//        final boolean[] snapped = {false};
//        Player nonCurrentPlayer = isPlayer1Turn ? player2 : player1;
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (!snapped[0]) {
//                    System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
//                    System.out.println("Press Enter to continue...");
//                    nonCurrentPlayer.incrementScore();
//                }
//            }
//        }, 2000);
//
//        try {
//            String input = scanner.nextLine().trim();
//            if (input.equalsIgnoreCase("snap")) {
//                snapped[0] = true;
//            }
//        } catch (Exception e) {
//            System.err.println("Error reading input: " + e.getMessage());
//        }
//
//        return snapped[0];
//    }

    public static void main(String[] args) {

        Snap snapGame = new Snap();
        snapGame.playGame();

    } // end snap main

} // end class
