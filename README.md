<div align="center">
  <h1>♠️ Java Snap ♥️</h1>
  <p>The classic card-matching game brought to your terminal by Java</p>
</div>

<div align="center">
   <a href="#features">Features</a> •
   <a href="#tech">Tech</a> •
   <a href="#How-To-Play">How To Play</a> •
   <a href="#local-development">Local Development</a> •
   <a href="#testing">Testing</a> •
   <a href="#architecture-and-implementation">Architecture</a> •
   <a href="#Future-Roadmap">Future</a>
</div>

## Features

- 2 player mode by default.
- Players can enter and save player names on multiple rounds.
- Keep track of scores for each player across multiple rounds.
- CardGame class base for other card games to be built upon easily.
- Built-in timer function for entering "snap" by player within 2 seconds or they lose.
- If a game cycles through a whole deck of 52 cards then it will automatically reshuffle & game continues.
- Players can either easily play again or quit the game when they win or lose.

## Tech
- Java 8 or higher
- JUnit 5 for testing
- Maven for dependency management
- Intellij IDEA for development

## How To Play

### Game Rules

- Two players take turns drawing cards by pressing 'Enter'.
- When two consecutive cards match in rank (example: two Kings), player has 2 secs to type "snap".
- If the player types "snap" correctly within 2 seconds they win the round and score a point.
- If the entire deck is dealt without a match, it's reshuffled automatically.
- After each round, you can choose to play again or exit.

### Gameplay Flow

- Enter names for both players when prompted.
- Follow the on-screen instructions to begin the game.
- Player 1 starts, followed by alternating turns.
- Press 'Enter' on your turn to deal a card.
- When consecutive cards match, quickly type "snap" and press 'Enter'.
- After a win or loss, choose to play again (Y) or quit (N).

## Local Development

### Prerequisites

- Java JDK 8 or higher
- Maven (for dependency management)
- Git (for cloning the repository)
- Intellij (for development - recommended)
  
### Set Up Local Development Environment

Clone the repository to a local directory:  
```git clone https://github.com/sidendev/java-snap.git```

Running the Game Locally:  
Using IntelliJ IDEA (Recommended)

- Open the project in IntelliJ IDEA, navigate via File => Open
- Locate the Snap.java file in the project explorer
- Right-click on the file and select "Run Snap.main()"
- The game will start in the integrated terminal

If needed see the pom.xml file for dependencies required to run the project.
install any dependencies required to run the project that you do not yet already have installed.

The game has features built in that allow for ending the game after a player wins or loses.  
If though you would like to end the game early, you can press the stop button that should be in the bottom left corner of your IDE. This will end the game and exit the system run.

## Testing

- Testing is implemented using JUnit.
- Ensure you have the correct version of JUnit installed if you want to run the test files. You can check this in the pom.xml file.
- The SnapTest class contains unit tests for the Snap class.
- Currently, only non-dynamic methods are tested, as methods requiring user input in the terminal have not yet been included.
- To run the tests:
  - Right-click on SnapTest.java or CardGameTest.java in your IDE.
  - Select "Run (Test)" to execute the tests.
  - The test results should be displayed in your IDE.
- Future Testing improvements:
  - User input testing will be added in a future update.
  - Additional methods in the Snap class will be tested in future revisions.

## Architecture And Implementation

### Architecture Overview

- CardGame: Base class providing card deck management functionality
- Snap: Main game class implementing the Snap-specific rules and game play
- Card, CardSuit, CardSymbol, CardValue: Card representation of a real life card
- Player: Player tracking with name and score
- SortingBySuit: Comparator to sort cards by suit  

### Exception Handling & Multithreading

- One of the most interesting (and challenging) aspects of this game is the use of multithreading to handle time-limited input for the "snap" mechanic:
  - Created a separate thread to handle user input.
  - Uses 'synchronized' to coordinate between the main game thread and input thread.
  - Implements a timeout mechanism (2 seconds) wait for the "snap" input and handles when main thread can start running again.   


- The game implements comprehensive exception handling and validation to handle any potential errors:
  - Empty or invalid player names are handled with clear error messages.
  - Duplicate player names are detected and prevented.
  - Timeout handling for the "snap" input ensures the game continues as smoothly as possible.
  - New scanner creation after interruption to ensure consistent input handling.

## Future Roadmap

- Add a PlayersTable class to allow players more functionality and have access to other methods in the game.
- Add other card games that can be built upon the CardGame base such as Blackjack.
- Allow players to alter the time they have to input 'snap'.
- Possibly create a GUI for the game using JavaFX.



