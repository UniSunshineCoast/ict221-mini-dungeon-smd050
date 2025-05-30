package dungeon.engine;

import java.io.Serializable;

public interface MapItem extends Serializable {
    /// gets symbol of other map items
    String getSymbol();
    /// Player interactions
    String interact (Player player);
    ///  Checks player parameter to not go out of bounds
    boolean wallBoundary();

}
