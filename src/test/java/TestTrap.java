import dungeon.engine.Player;
import dungeon.engine.Trap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrap {



    @Test
    void testInteract() {
        Player player = new Player(1,1);
        Trap trap = new Trap();

        int initialHp = player.getHealth();
        String result = trap.interact(player);

        assertEquals("A trap caught you!", result, "Trap should return interaction message");
        assertEquals(initialHp - 2, player.getHealth(), "Trap should decrease player's HP by 2");
    }

    @Test
    void testWallBoundary() {
        Trap trap = new Trap();
        assertFalse(trap.wallBoundary(), "Trap should not block movement (not a wall)");
    }
}
