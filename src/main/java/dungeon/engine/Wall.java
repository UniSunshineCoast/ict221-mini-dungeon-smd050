package dungeon.engine;

import java.io.Serializable;

/**
 *  Blocks Movement.
 */
public class Wall implements MapItem, Serializable {

    @Override
    public String getSymbol() {
        return "#";
    }
    @Override
    public String interact (Player player){

        return "You stared at a wall, unable to move that way.";
    }
    @Override
    public boolean wallBoundary() {
        return true;

    }
}
