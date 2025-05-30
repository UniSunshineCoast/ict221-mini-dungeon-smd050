package dungeon.engine;

/**
 *  Entry Point to the Maze
 */
public class Entry implements MapItem{

    @Override
    public String getSymbol() {
        return "E";
    }

    @Override
    public String interact (Player player){
        return "You have entered the dungeon!";
    }

    @Override
    public boolean wallBoundary() {
        return false;
    }
}
