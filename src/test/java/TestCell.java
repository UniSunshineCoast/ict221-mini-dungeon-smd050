import dungeon.engine.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestCell {

    @Test
    void testSetAndGetEntity() {
        Cell cell = new Cell();
        Gold gold = new Gold();
        cell.setEntity(gold);
        assertEquals(gold, cell.getEntity());
    }

    @Test
    void testIsBlockingWithWall() {
        Cell cell = new Cell();
        cell.setEntity(new Wall());
        assertTrue(cell.isBlocking());
    }

    @Test
    void testIsBlockingWithoutWall() {
        Cell cell = new Cell();
        cell.setEntity(new Gold()); // any non-wall MapItem
        assertFalse(cell.isBlocking());
    }

    @Test
    void testInteractWithEntity() {
        Cell cell = new Cell();
        Player player = new Player(0,6);
        cell.setEntity(new HealthPotion());
        String result = cell.interact(player);
        assertEquals("Restored 4 hp!", result);
    }

    @Test
    void testInteractWithoutEntity() {
        Cell cell = new Cell();
        Player player = new Player(7,3);
        String result = cell.interact(player);
        assertEquals("Nothing here.", result);
    }
}
