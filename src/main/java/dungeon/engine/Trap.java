package dungeon.engine;

import java.io.Serializable;

/**
 *  Decreases Player's HP
 */
public class Trap implements MapItem, Serializable {

    @Override
    public String getSymbol() {
        return "T";
    }
    @Override
    public String interact (Player player){
        player.updateHp(-2);
        return "A trap caught you!";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }

}
