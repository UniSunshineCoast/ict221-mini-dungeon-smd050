package dungeon.engine;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {
    private MapItem entity;

    public Cell() {
        this.entity = null;
    }

    public MapItem getEntity() {
        return entity;
    }

    public void setEntity(MapItem entity) {
        this.entity = entity;
    }

    public boolean isBlocking() {
        return entity instanceof Wall;
    }

    public String interact(Player player) {
        if (entity != null) {
            return entity.interact(player);
        }
        return "Nothing here.";
    }
}