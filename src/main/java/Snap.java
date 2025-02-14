import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Snap extends CardGame {
    private ArrayList<Card> dealtCards;
    private Scanner scanner;
    private int cardsDealtCount; // to track how many cards have been dealt so far
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn; // boolean - tracking turns

    // Constructor
    public Snap() {
        super("Snap");
        this.dealtCards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.cardsDealtCount = 0; // starting the counter at 0

        // ask for player names - how to set this up ---- should be a method that gets called ----
        System.out.println();
        System.out.println("Welcome to Java Snap!");
        System.out.println();
        System.out.println("Players, lets get ready.");
        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter Player 2 name: ");
        String name2 = scanner.nextLine();

        this.player1 = new Player(name1); // creating instances of the players
        this.player2 = new Player(name2); // another Player instance
        this.isPlayer1Turn = true; // player 1 will always start first time on game load
    }

    // restart game method
    private void restartGame() {
        shuffleDeck(); // shuffle deck
        dealtCards.clear(); // clear dealt cards so far
        cardsDealtCount = 0; // reset the count
//        isPlayer1Turn = true; // reset turn order
        playGame(); // restart game
    }

    // playGame method
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

        scanner.nextLine(); // this will trigger when enter is pressed

        // loop here of every card dealt and checking if winner - will keep looping through the deckOfCards
        while (true) {
            // saying whose turn it is
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            System.out.println(currentPlayer.getName() + "'s turn. Press Enter to get a card...");
            scanner.nextLine(); // wait for Enter press

            Card newCard = dealCard(); // getting the next card
            System.out.println("Card dealt: " + newCard); // change wording on this WIP
            cardsDealtCount++; // increment the counter by 1
            System.out.println();

            // checking if there was a previous card in dealtCards arrayList and if they match

//            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
//                System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");
//
//                if (playerWritesSnap()) {
//                    System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
//                    currentPlayer.incrementScore();
//                    break;
//                } else {
//                    System.out.println("Oh no! You didn't type 'snap' in time. You lose this time :(");
//                }
//            }

            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
                System.out.println("Looks like a SNAP! Type 'snap' within 2 seconds!");

                if (playerWritesSnap()) {
                    System.out.println("Well done, " + currentPlayer.getName() + "! You typed 'snap' in time. You win!");
                    currentPlayer.incrementScore();
                } else {
                    System.out.println("Oh no! You lose! You didn't write 'snap' in time.");
                    System.out.println("Would you like to play again? (Y/N)");

                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("Y")) {
                        restartGame(); // restart game method - without resetting player scores
                    } else {
                        System.out.println("Thanks for playing!");
                        scanner.close();
                        return;
                    }
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

            // scanner.nextLine(); // getting user enter input = not sure if we need this
        }

        // need to fix this part now - unreachable -----------------------------
//        System.out.println("Game over. Would you like to play again? (Y/N)");
//        String response = scanner.nextLine();
//        if (response.equalsIgnoreCase("Y")) {
//            shuffleDeck();
//            dealtCards.clear();
//            cardsDealtCount = 0;
//            playGame();
//        } else {
//            System.out.println("Thanks for playing!");
//            scanner.close();
//        }


    } // playGame end

    // running timer - set up playerWritesSnap - checking if they type snap in time - we get boolean result
    // default is false
    private boolean playerWritesSnap() {
        final boolean[] snappedInTime = {false};
        Timer timer = new Timer(); // using Timer object - java util
        // separate thread set up to handle user input
        Thread inputThread = new Thread(() -> {
            try {
                String userInput = scanner.nextLine(); // wait for player input
                if (userInput.equalsIgnoreCase("snap")) {
                    snappedInTime[0] = true;
                }
            } catch (Exception e) {
                // if the thread is interrupted, we return
            }
        });

        inputThread.start(); // starting the thread

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inputThread.interrupt(); // should stop input thread - delayed by 2000 milliseconds
            }
        }, 2000); // 2 seconds timeout - can change this

        try {
            inputThread.join(2000); // holds up main thread for 2 seconds
        } catch (InterruptedException e) {
            return false; // should make playerWritesSnap be false
        }

        timer.cancel(); // stopping the timer

        return snappedInTime[0]; // if true returned player wrote snap in time, if false they did not




    } // end playGame




    public static void main(String[] args) {

        // when this file is run the game should start, a fresh new CardGame - deckOfCards should get created.
        Snap snapGame = new Snap();
        snapGame.playGame();



    } // snap main



} // end class
