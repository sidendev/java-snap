import java.util.ArrayList;
import java.util.Scanner;

public class Snap extends CardGame {
    private ArrayList<Card> dealtCards;
    private Scanner scanner;
    private int cardsDealtCount; // to track how many cards have been dealt so far

    // Constructor
    public Snap() {
        super("Snap");
        this.dealtCards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.cardsDealtCount = 0; // starting the counter at 0
    }

    // playGame method
    public void playGame() {
        System.out.println();
        System.out.println("Welcome to Java Snap!");
        System.out.println();
        System.out.println("How to play Java Snap:");
        System.out.println("Press Enter to be dealt a card.");
        System.out.println("If two consecutive cards match, you win!");
        System.out.println("Press Enter to begin...");

        scanner.nextLine(); // this will trigger when enter is pressed

        // loop here of every card dealt and checking if winner - will keep looping through the deckOfCards

        while (true) {
            Card newCard = dealCard(); // getting the next card
            System.out.println("Card dealt: " + newCard); // change wording on this WIP
            cardsDealtCount++; // increment the counter by 1

            // checking if there was a previous card in dealtCards arrayList and if they match
            if (!dealtCards.isEmpty() && dealtCards.getLast().getSymbol().equals(newCard.getSymbol())) {
                System.out.println("Snap! You win!");
                break;
            }

            dealtCards.add(newCard); // storing the card for comparison

            // checking if 52 cards have been dealt
            if (cardsDealtCount == 52) {
                System.out.println("Wow, this game is going on for a long time!");
                System.out.println("The dealer has now shuffled the deck. Press Enter to continue...");

                shuffleDeck(); // shuffle the deck
                dealtCards.clear(); // resetting the list of dealt cards
                cardsDealtCount = 0; // resetting the counter

                // scanner.nextLine(); // get player to press enter to continue
            }


            scanner.nextLine(); // getting user enter input
        }

        System.out.println("Game over. Would you like to play again? (Y/N)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("Y")) {
            shuffleDeck(); // shuffle the deck
            dealtCards.clear(); // resetting the list of dealt cards
            cardsDealtCount = 0; // resetting the counter
            playGame(); // Restart the game
        } else {
            System.out.println("Thanks for playing!");
            scanner.close();
        }



    } // end playGame




    public static void main(String[] args) {

        // when this file is run the game should start, a fresh new CardGame - deckOfCards should get created.
        Snap snapGame = new Snap();
        snapGame.playGame();



    } // snap main



} // end class
