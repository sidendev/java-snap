public class Player {
    // fields here for the players, there will be player 1 and player 2
    // allow the user to enter names for player 1 and player 2
    private String name;
    private int score;

    // constructor
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    // to be set up - maybe
    public int setScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

} // end class

