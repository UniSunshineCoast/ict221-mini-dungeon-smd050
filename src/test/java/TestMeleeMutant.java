
import dungeon.engine.MeleeMutant;
import dungeon.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMeleeMutant {

    private MeleeMutant mutant;
    private Player player;

    @BeforeEach
    void setUp() {
        mutant = new MeleeMutant();
        player = new Player(0, 0);  // arbitrary position
    }

    @Test
    void testGetSymbol() {
        assertEquals("M", mutant.getSymbol());
    }

    @Test
    void testInteractModifiesPlayer() {
        int initialHp = player.getHealth();
        int initialScore = player.getScore();

        String result = mutant.interact(player);

        assertEquals(initialHp - 2, player.getHealth());
        assertEquals(initialScore + 2, player.getScore());
        assertTrue(result.contains("melee mutant"));
    }

    @Test
    void testWallBoundaryIsFalse() {
        assertFalse(mutant.wallBoundary());
    }
}
