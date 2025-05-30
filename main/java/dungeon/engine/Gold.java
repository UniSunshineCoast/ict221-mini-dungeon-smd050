package dungeon.engine;

import java.io.Serializable;

/**
 *  Increases Player Score
 */
public class Gold implements MapItem, Serializable {

    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public String interact (Player player){
        player.addNewScore(2);
        return "Found Gold! score +2";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }

}
