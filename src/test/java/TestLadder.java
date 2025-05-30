
import dungeon.engine.Ladder;
import dungeon.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLadder {

    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player(0, 0);
    }

    @Test
    public void testInteract_nonFinalLevel_setsAdvanceToNextLevel() {
        Ladder ladder = new Ladder(false);

        String message = ladder.interact(player);

        assertTrue(player.moveToLevel2(), "Player should be marked to move to level 2");
        assertEquals("You climbed a ladder, Bringing you one more way out!", message);
    }

    @Test
    public void testInteract_finalLevel_setsFinished() {
        Ladder ladder = new Ladder(true);

        String message = ladder.interact(player);

        assertTrue(player.isFinished(), "Player should be marked as finished");
        assertEquals("", message);
    }

    @Test
    public void testGetSymbol_returnsL() {
        Ladder ladder = new Ladder(false);
        assertEquals("L", ladder.getSymbol());
    }

    @Test
    public void testWallBoundary_returnsFalse() {
        Ladder ladder = new Ladder(false);
        assertFalse(ladder.wallBoundary());
    }
}
