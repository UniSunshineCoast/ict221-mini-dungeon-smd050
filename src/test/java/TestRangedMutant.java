
import dungeon.engine.Player;
import dungeon.engine.RangedMutant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRangedMutant {

    private RangedMutant mutant;
    private Player player;

    @BeforeEach
    void setUp() {
        mutant = new RangedMutant();
        player = new Player(5, 5);
    }

    @Test
    void testGetSymbol() {
        assertEquals("R", mutant.getSymbol());
    }

    @Test
    void testInteractAddsScore() {
        int initialScore = player.getScore();
        String message = mutant.interact(player);

        assertEquals(initialScore + 2, player.getScore());
        assertTrue(message.contains("ranged mutant"));
    }

    @Test
    void testTryAttackInRange_HitOrMiss() {
        // Mutant at (5,5), player at (5,7) — 2 tiles down
        int originalHp = player.getHealth();
        String result = mutant.tryAttack(player, 5, 5, 5, 7);


        if (result.contains("Hit")) {
            assertEquals(originalHp - 2, player.getHealth());
        } else if (result.contains("Missed")) {
            assertEquals(originalHp, player.getHealth());
        } else {
            fail("Expected hit or miss message, got: " + result);
        }
    }

    @Test
    void testTryAttackOutOfRange() {
        int originalHp = player.getHealth();
        String result = mutant.tryAttack(player, 5, 5, 5, 8);  // 3 tiles away

        assertEquals("", result);
        assertEquals(originalHp, player.getHealth());
    }
}
