/***
 * University of the Sunshine Coast
 * Java Application by Sean De Guzman
 * 1171469
 * 05/07/2025
 * ***/
package dungeon.engine;

import javafx.scene.text.Text;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class GameEngine implements Serializable {

    /**
     * An example board to store the current game state.
     *
     * Note: depending on your game, you might want to change this from 'int' to String or something?
     */
    public transient Cell[][] map;
    private Player player;
    private int currentLevel = 1;
    private static final int finalLevel = 2;
    private static FileManager fileManager;
    private static final long serialVersionUID = 1L;
    private CellData[][] serializableMap; // for saving




// <editor-fold desc="Map Instance">

    /**
     * Creates a square game board.
     *
     * @param size the width and height.
     */
    public GameEngine(int size) {
        map = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell();
                Text text = new Text(i + "," + j);
                cell.getChildren().add(text);
                map[i][j] = cell;
            }
        }

        map[0][0].setStyle("-fx-background-color: #7baaa4");
        map[size-1][size-1].setStyle("-fx-background-color: #7baaa4");
        player = new Player(0, map.length - 1); // bottom-left corner
    }

    /**
     * The size of the current game.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return map.length;
    }

    /**
     * The map of the current game.
     *
     * @return the map, which is a 2d array.
     */
    public Cell[][] getMap() {
        return map;
    }


    /**
     *  Handle Game State and Save or Load
     * **/
    public void saveGame() {
        this.serializableMap = new CellData[getSize()][getSize()];
        for (int y = 0; y < getSize(); y++) {
            for (int x = 0; x < getSize(); x++) {
                Cell cell = map[y][x];
                CellData cellData = new CellData();
                cellData.setEntity(cell.getEntity());
                serializableMap[y][x] = cellData;
            }
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/game.dat"))) {
            out.writeObject(this);
            System.out.println("Game successfully saved!");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }
    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

    }

    public static GameEngine loadGame() {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/game.dat"))) {
            GameEngine loaded = (GameEngine) in.readObject();
            loaded.map = new Cell[loaded.serializableMap.length][loaded.serializableMap[0].length];

            for (int y = 0; y < loaded.serializableMap.length; y++) {
                for (int x = 0; x < loaded.serializableMap[y].length; x++) {
                    CellData data = loaded.serializableMap[y][x];
                    Cell cell = new Cell();
                    cell.setEntity(data.getEntity());
                    loaded.map[y][x] = cell;
                }
            }
            System.out.println("Game successfully loaded!");
            return loaded;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game: " + e.getMessage());
            return null;
        }

    }

// </editor-fold>


// <editor-fold desc="Map Settings">

    public Cell[][] generateNewMap(int difficulty) {
        map = new Cell[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                map[i][j] = new Cell();
            }
        }
        populateMap(difficulty);
        player.moveTo(0, map.length - 1 );
        return map;
    }

    private void placeItem(MapItem entity, int x, int y) {
        map[y][x].setEntity(entity);
    }

    private void placeRandomItem(MapItem entity, int count) {
        Random rand = new Random();
        int size = map.length;

        while (count > 0) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);

            if (map[y][x].getEntity() == null && !(x == 0 && y == size - 1)) {
                map[y][x].setEntity(entity);
                count--;
            }
        }
    }

    private void populateMap(int difficulty) {
        boolean isFinalLevel = currentLevel == finalLevel;
        placeItem(new Entry(), 0, map.length - 1);

        placeRandomItem(new Ladder(isFinalLevel), 1);
        placeRandomItem(new Gold(), 5);
        placeRandomItem(new Trap(), 5);
        placeRandomItem(new MeleeMutant(), 3);
        placeRandomItem(new RangedMutant(), difficulty);
        placeRandomItem(new HealthPotion(), 2);
    }



    public String renderMap() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (player.getX() == x && player.getY() == y) {
                    sb.append("P "); // Player symbol
                } else {
                    MapItem entity = map[y][x].getEntity();
                    sb.append(entity != null ? entity.getSymbol() + " " : ". ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }




// </editor-fold>


// <editor-fold desc="Player Settings">

    public Player getPlayer() {
        return player;
    }

    public String getPlayerDetails() {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();
        int score = player.getScore();
        StringBuilder status = new StringBuilder("Health: [");

        for (int i = 0; i < maxHealth; i++) {
            if (i < health) {
                status.append("*");
            } else {
                status.append(" ");
            }
        }

        status.append("] " + health + "/" + maxHealth + " | Score: ").append(score);
        return status.toString();
    }


    public String movePlayer(String direction) {

        int dx = 0, dy = 0;

        ///switch case for movement, WASD settings for up, down, left, right.
        switch (direction.toLowerCase()) {
            case "w": dy = -1; break;
            case "s": dy = 1; break;
            case "a": dx = -1; break;
            case "d": dx = 1; break;
            case "": break;
            default: return "Invalid move command.";
        }

        player.addStep();

        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (newX < 0 || newX >= map.length || newY < 0 || newY >= map.length) {
            return "You tried to go out of bounds";
        }
        Cell target = map[newY][newX];
        if (target.isBlocking()) {
            return "You Stared at a wall menacingly";
        }
        player.moveTo(newX, newY);


        String message = target.interact(player);

        ///Don't Break Entrance or Traps
        if (!(target.getEntity() instanceof Entry || target.getEntity() instanceof Trap)) {
            target.setEntity(null);
        }


        StringBuilder attackMessages = new StringBuilder();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                MapItem entity = map[y][x].getEntity();
                if (entity instanceof RangedMutant) {
                    String attackResult = ((RangedMutant) entity).tryAttack(player, x, y, player.getX(), player.getY());
                    if (!attackResult.isEmpty()) {
                        attackMessages.append(attackResult).append("\n");
                    }
                }
            }
        }

        /**
         * Used for Testing Steps
         *      player.addStep(); player.addStep();player.addStep();player.addStep();player.addStep();player.addStep();player.addStep();player.addStep();player.addStep();player.addStep();
         *      System.out.println(player.getSteps());
         **/

        if (player.moveToLevel2()) {

            currentLevel++;
            generateNewMap(currentLevel + 2);
            player.setAdvanceToNextLevel(false);
            return "You climbed a ladder, Bringing you one more way out!" + "\n Difficulty + 2";
        }

        return "\nYou moved to " + newX + "," + newY + ".\n" + message + "\n" + attackMessages;
    }


// </editor-fold>


// <editor-fold desc="Initialise Game">

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose Your Difficulty From 1 to 10, Default is 3: ");
        String difficultyInput = scanner.nextLine().trim();
        int difficulty;

        try {
            difficulty = Integer.parseInt(difficultyInput);
            if (difficulty < 1 || difficulty > 10) {
                System.out.println("Difficulty Out of Range, starting at Difficulty 3.");
                difficulty = 3;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, starting at Difficulty 3.");
            difficulty = 3;
        }

        GameEngine engine = new GameEngine(10);
        engine.populateMap(difficulty); // Initial difficulty level

        fileManager = new FileManager();

        System.out.println("Welcome to MiniDungeon!");
        System.out.printf("MapSize: %d * %d\n", engine.getSize(), engine.getSize());

        while (true) {
            System.out.println("\nCurrent Map:");
            System.out.println(engine.renderMap());
            System.out.println(engine.getPlayerDetails());

            System.out.print("W/A/S/D to move, or type 'Quit', 'Display', 'SaveGame', or 'LoadGame': ");
            String movement = scanner.nextLine().trim();

            if (movement.equalsIgnoreCase("Quit")) {
                System.out.println("Thanks For Playing!");
                break;

            } else if (movement.equalsIgnoreCase("Display")) {
                fileManager.displayTopScores();
                continue;

            } else if (movement.equalsIgnoreCase("SaveGame")) {
                engine.saveGame();
                continue;

            } else if (movement.equalsIgnoreCase("LoadGame")) {
                GameEngine loaded = GameEngine.loadGame();
                if (loaded != null) engine = loaded;
                continue;
            }


            String result = engine.movePlayer(movement);
            System.out.println(result);

            if (engine.getPlayer().isFinished()) {
                System.out.println("Level 2 Ladder Reached, You Are Out The Dungeon!");
                FileManager fileManager = new FileManager();
                fileManager.addNewScore(engine.getPlayer().getScore());
                fileManager.displayTopScores();
                break;
            } else if (engine.getPlayer().isDead()) {
                System.out.println("You Died! GameOver.");
                break;
            } else if (engine.getPlayer().isOutOfSteps()) {
                System.out.println("You Have Ran Out Of Steps, GameOver.");
                break;
            }
        }

    }



// </editor-fold>


} // End Bracket
