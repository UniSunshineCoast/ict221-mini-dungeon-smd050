import dungeon.engine.Entry;
import dungeon.engine.Player;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestEntry {

    @Test
    void testGetSymbol() {
        Entry entry = new Entry();
        assertEquals("E", entry.getSymbol());
    }

    @Test
    void testInteract() {
        Entry entry = new Entry();
        Player player = new Player(5,7);  // dummy data
        assertEquals("You have entered the dungeon!", entry.interact(player));
    }

    @Test
    void testWallBoundary() {
        Entry entry = new Entry();
        assertFalse(entry.wallBoundary());
    }
}
