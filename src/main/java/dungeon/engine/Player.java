package dungeon.engine;

import java.io.Serializable;

/**
 *  The Player
 */
public class Player implements  Serializable {
    /**
     *  Player properties
     */
    private int x; //Player Coordinates
    private int y;
    private int hp = 10;
    private static final int maxHp = 10; //End-game setup (Death)
    private int score = 0;
    private int steps = 0;
    private static final int maxSteps = 100; //End-game setup (Out of Steps)
    private boolean finished;
    private boolean moveNextLevel;

// <editor-fold desc="Player Movements">

    /**
     *  Player Movements
     */
    //Set Player with initial position (X,Y) coordinates
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    //Move Player upon request with new coordinates
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;

    }

    // Constructors for player position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

// </editor-fold>

// <editor-fold desc="Player Steps">
    /**
     *  Player step counter
     */
    public int getSteps() {
        return steps;
    }

    // add steps to counter
    public void addStep() {
        steps++;
    }

    // player running out of steps
    public boolean isOutOfSteps() {
        return steps >= maxSteps;
    }

// </editor-fold>

// <editor-fold desc="Player Vitality">

    /**
     *  Player Vitality
     */
    public int getHealth() {
        return hp;
    }

    // set player health
    public int getMaxHealth() {
        return maxHp;
    }
    //Player changes HP based on event - MAX HP gain = 10
    public void updateHp(int initial) {
        hp = Math.min(10, hp + initial);
    }

    //Player is 'Dead' at 0 HP - Returns True
    public boolean isDead() {
        return hp <= 0;
    }

// </editor-fold>

// <editor-fold desc="Player Score">

    /**
     *  Player Score
     */
    public int getScore() {
        return score;
    }

    //Player earned score based on event
    public void addNewScore(int scoreEarned) {
        score += scoreEarned;
    }

// </editor-fold>

// <editor-fold desc="Player State">
    /**
     *  Game State Settings
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setAdvanceToNextLevel(boolean advanceToNextLevel) {
        this.moveNextLevel = advanceToNextLevel;
    }

    public boolean moveToLevel2() {
        return moveNextLevel;
    }

// </editor-fold>

}//End Bracket