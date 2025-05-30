import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import dungeon.engine.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGameEngine {

    private GameEngine engine;

    @BeforeEach
    void setUp() {
        engine = new GameEngine(5);  // small test map
        engine.generateNewMap(3);
    }

    @Test
    void testGetSize() {
        assertEquals(5, engine.getSize());
    }

    @Test
    void testGetMapNotNull() {
        assertNotNull(engine.getMap());
    }

    @Test
    void testRenderMap() {
        String output = engine.renderMap();
        assertTrue(output.contains("P")); // Player should be visible
    }

    @Test
    void testGetPlayerDetailsFormat() {
        String status = engine.getPlayerDetails();
        assertTrue(status.contains("Health: ["));
        assertTrue(status.contains("Score:"));
    }

    @Test
    void testMovePlayerInvalidCommand() {
        String result = engine.movePlayer("x");
        assertEquals("Invalid move command.", result);
    }

    @Test
    void testMovePlayerOutOfBounds() {
        engine.getPlayer().moveTo(0, 0);
        String result = engine.movePlayer("w"); // upward out-of-bounds
        assertEquals("You tried to move outside the map!", result);
    }

    @Test
    void testMovePlayerIntoEmptyCell() {
        engine.getPlayer().moveTo(0, 4);
        engine.getMap()[3][0].setEntity(null); // clear target cell
        String result = engine.movePlayer("w");
        assertTrue(result.contains("You moved to"));
    }

    @Test
    void testMovePlayerIntoWall() {
        engine.getMap()[3][0].setEntity(new Wall());
        String result = engine.movePlayer("w");
        assertEquals("You tried to move but it is a wall.", result);
    }

    @Test
    void testSaveAndLoadGame() {
        engine.saveGame();
        GameEngine loaded = GameEngine.loadGame();
        assertNotNull(loaded);
        assertEquals(engine.getSize(), loaded.getSize());
    }

    @Test
    void testGenerateNewMapChangesEntities() {
        Cell[][] oldMap = engine.getMap();
        engine.generateNewMap(4);
        Cell[][] newMap = engine.getMap();
        assertNotSame(oldMap, newMap);
    }

    @Test
    void testPlayerAdvancement() {
        engine.getPlayer().setAdvanceToNextLevel(true);
        String result = engine.movePlayer("");
        assertTrue(result.contains("Bringing you one more way out!"));
    }

    @Test
    void testPlayerFinishTriggersScore() {
        engine.getPlayer().setFinished(true);
        assertTrue(engine.getPlayer().isFinished());
    }
}
