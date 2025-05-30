package dungeon.engine;

import java.io.Serializable;

public class HealthPotion implements MapItem, Serializable {
    @Override
    public String getSymbol() {
        return "H";
    }

    @Override
    public String interact(Player player){
        player.updateHp(4);
        return "Restored 4 hp!";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }
}
