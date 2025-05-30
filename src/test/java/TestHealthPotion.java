
import dungeon.engine.HealthPotion;
import dungeon.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHealthPotion {

    private Player player;
    private HealthPotion potion;

    @BeforeEach
    public void setup() {
        player = new Player(0, 0);
        potion = new HealthPotion();
    }

    @Test
    public void testInteract_restoresHealth() {
        player.updateHp(-4); // Reduce health
        int hpBefore = player.getHealth();

        String message = potion.interact(player);

        assertEquals(hpBefore + 4, player.getHealth(), "Health should increase by 4");
        assertEquals("Restored 4 hp!", message);
    }

    @Test
    public void testGetSymbol_returnsH() {
        assertEquals("H", potion.getSymbol());
    }

    @Test
    public void testWallBoundary_returnsFalse() {
        assertFalse(potion.wallBoundary());
    }
}
