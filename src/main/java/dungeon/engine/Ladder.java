package dungeon.engine;

import java.io.Serializable;

/**
 *  Advances to the next level or exits the game.
 */
public class Ladder implements MapItem, Serializable {

    public boolean isFinalLevel = false;

    public Ladder(boolean isFinalLevel2) {
        isFinalLevel = isFinalLevel2;
    }


    @Override
    public String getSymbol() {
        return "L";
    }

    @Override
    public String interact (Player player){
        if(isFinalLevel){
            player.setFinished(true);
            return "";
        }
        else{
            player.setAdvanceToNextLevel(true);
            return "You climbed a ladder, Bringing you one more way out!";
        }
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }

}
