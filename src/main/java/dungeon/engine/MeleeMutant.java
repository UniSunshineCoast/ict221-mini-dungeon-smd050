package dungeon.engine;

import java.io.Serializable;

/**
 *  Stationary, add score and reduce HP
 */
public class MeleeMutant implements MapItem, Serializable {

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public String interact (Player player){
        player.addNewScore(2);
        player.updateHp(-2);
        return "You Fought against a melee mutant, -2hp and +2 Score!";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }

}
