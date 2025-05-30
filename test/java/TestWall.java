import dungeon.engine.Player;
import dungeon.engine.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestWall {



    @Test
    void testInteract() {
        Wall wall = new Wall();
        Player dummyPlayer = new Player(1,1);
        String expectedMessage = "You stared at a wall, unable to move that way.";
        assertEquals(expectedMessage, wall.interact(dummyPlayer), "Wall interaction message mismatch");
    }

    @Test
    void testWallBoundary() {
        Wall wall = new Wall();
        assertTrue(wall.wallBoundary(), "Wall should be a boundary (blocking movement)");
    }
}
