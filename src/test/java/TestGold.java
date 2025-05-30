
import dungeon.engine.Gold;
import dungeon.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGold {

    private Player player;
    private Gold gold;

    @BeforeEach
    public void setup() {
        player = new Player(0, 0);
        gold = new Gold();
    }

    @Test
    public void testInteract_increasesScore() {
        int initialScore = player.getScore();

        String result = gold.interact(player);

        assertEquals(initialScore + 2, player.getScore(), "Player score should increase by 2");
        assertEquals("Found Gold! score +2", result);
    }

    @Test
    public void testGetSymbol_returnsG() {
        assertEquals("G", gold.getSymbol());
    }

    @Test
    public void testWallBoundary_returnsFalse() {
        assertFalse(gold.wallBoundary());
    }
}
