package dungeon.engine;

import java.io.Serializable;

public class CellData implements Serializable {
    private static final long serialVersionUID = 1L;

    private MapItem entity;

    public CellData() {
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