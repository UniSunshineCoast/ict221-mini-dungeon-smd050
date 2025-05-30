package dungeon.gui;
import dungeon.engine.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class Controller {
    @FXML
    private GridPane gridPane;

    @FXML
    private TextArea txta_Logs;

    @FXML
    private TextField txtf_Health, txtf_Score, txtf_HighScore, txtf_Steps;

    @FXML
    private Button move_Up_Button, move_Down_Button, move_Left_Button, move_Right_Button;

    private Player player;


    GameEngine engine;

// <editor-fold desc="Map">
    @FXML
    public void initialize() {
        engine = new GameEngine(10);

        WelcomePlayer();

        //UI append text area or text field objects
        Update_HighScore();
        Update_Score();
        Update_Player_Heath();
        disableMoveButtons();


        refreshGui();


    }


    private void generateNewMap(int difficulty) {
        Cell[][] map = engine.generateNewMap(difficulty);
        gridPane.getChildren().clear();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Cell cell = map[y][x];

                // Default image for empty tiles
                String imageFile = "plank_icon.png";



                if (cell.getEntity() != null) {
                    String className = cell.getEntity().getClass().getSimpleName();

                    switch (className) {
                        case "Entry":           imageFile = "entry_icon.png"; break;
                        case "Gold":            imageFile = "gold_icon.png"; break;
                        case "HealthPotion":    imageFile = "health_icon.png"; break;
                        case "Ladder":          imageFile = "ladder_icon.png"; break;
                        case "MeleeMutant":     imageFile = "melee_enemy.png"; break;
                        case "RangedMutant":    imageFile = "ranged_enemy.png"; break;
                        case "Trap":            imageFile = "trap_icon.png"; break;
                        case "Wall":            imageFile = "wall_icon.png"; break;

                    }
                    if (engine.getPlayer().getX() == x && engine.getPlayer().getY() == y) {
                        imageFile = "player_icon.png";
                    }
                }

                Image image = new Image(getClass().getResourceAsStream("/" + imageFile));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(85);
                imageView.setFitHeight(85);

                StackPane cellPane = new StackPane(imageView);
                cellPane.setPrefSize(85, 85);
                cellPane.setStyle("-fx-border-color: black;");
                gridPane.add(cellPane, x, y);

            }

        }
        enableMoveButtons();

    }


    private void refreshGui() {
        //Clear old GUI grid pane
        gridPane.getChildren().clear();

    }

    private void updateGui(){
        Cell[][] map = engine.getMap();
        gridPane.getChildren().clear();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Cell cell = map[y][x];

                // Default image for empty tiles
                String imageFile = "plank_icon.png";



                if (cell.getEntity() != null) {
                    String className = cell.getEntity().getClass().getSimpleName();

                    switch (className) {
                        case "Entry":           imageFile = "entry_icon.png"; break;
                        case "Gold":            imageFile = "gold_icon.png"; break;
                        case "HealthPotion":    imageFile = "health_icon.png"; break;
                        case "Ladder":          imageFile = "ladder_icon.png"; break;
                        case "MeleeMutant":     imageFile = "melee_enemy.png"; break;
                        case "RangedMutant":    imageFile = "ranged_enemy.png"; break;
                        case "Trap":            imageFile = "trap_icon.png"; break;
                        case "Wall":            imageFile = "wall_icon.png"; break;

                    }


                }
                if (engine.getPlayer().getX() == x && engine.getPlayer().getY() == y) {
                    imageFile = "player_icon.png";
                }

                Image image = new Image(getClass().getResourceAsStream("/" + imageFile));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(85);
                imageView.setFitHeight(85);

                StackPane cellPane = new StackPane(imageView);
                cellPane.setPrefSize(85, 85);
                cellPane.setStyle("-fx-border-color: black;");
                gridPane.add(cellPane, x, y);

            }

        }
    }
// </editor-fold>

// <editor-fold desc="Buttons">

    /// Sends Input for Settings
    public void btnEnter() {
        String allText = txta_Logs.getText().trim();
        if (allText.isEmpty()) {
            return;
        }

        String[] lines = allText.split("\\r?\\n");
        String lastLine = lines[lines.length - 1].trim();

        try {
            int difficulty = Integer.parseInt(lastLine);

            if (difficulty < 1 || difficulty > 10) {
                txta_Logs.appendText("\nInvalid difficulty input: 1 - 10\n");
                return;
            }

            generateNewMap(difficulty);
            txta_Logs.appendText("\nDifficulty set to " + difficulty + ".\nMap generated!");
        } catch (NumberFormatException e) {
            txta_Logs.appendText("\nInvalid Difficulty Input: 1 - 10\n");
        }
    }

    /**
     * Functions/Behaviours for player movement buttons
     */
    public void btnMove_Player_Up (){
        Update_Steps();
        txta_Logs.appendText("\n Player Moved North.");
        String result = engine.movePlayer("w");
        txta_Logs.appendText("\n" + result + "\n");
        if (engine.getPlayer().isDead() || engine.getPlayer().isFinished() || engine.getPlayer().isOutOfSteps()) {
            handleGameOver();
        } else {
            updateGui();
            Update_Player_Heath();
            Update_Score();
        }
    }
    public void btnMove_Player_Left () {
        Update_Steps();
        txta_Logs.appendText("\n Player Moved West.");
        String result = engine.movePlayer("a");
        txta_Logs.appendText("\n" + result + "\n");
        if (engine.getPlayer().isDead() || engine.getPlayer().isFinished() || engine.getPlayer().isOutOfSteps()) {
            handleGameOver();
        } else {
            updateGui();
            Update_Player_Heath();
            Update_Score();
        }
    }
    public void btnMove_Player_Right (){
        Update_Steps();
        txta_Logs.appendText("\n Player Moved East.");
        String result = engine.movePlayer("d");
        txta_Logs.appendText("\n" + result + "\n");
        if (engine.getPlayer().isDead()|| engine.getPlayer().isFinished() || engine.getPlayer().isOutOfSteps()) {
            handleGameOver();
        } else {
            updateGui();
            Update_Player_Heath();
            Update_Score();
        }
    }
    public void btnMove_Player_Down (){
        Update_Steps();
        txta_Logs.appendText("\n Player Moved South.");
        String result = engine.movePlayer("s");
        txta_Logs.appendText("\n" + result + "\n");
        if (engine.getPlayer().isDead() || engine.getPlayer().isFinished() || engine.getPlayer().isOutOfSteps()) {
            handleGameOver();
        } else {
            updateGui();
            Update_Player_Heath();
            Update_Score();
        }
    }

    /// Save Game State
    public void btnSave_Game() {
        txta_Logs.appendText("\nSaving Game...");
        if (engine != null) {
            engine.saveGame();
            txta_Logs.appendText("\nGame Saved Successfully.");
        } else {
            txta_Logs.appendText("\nError: Game Engine not initialized.");
        }
    }

    /// Load in Last Saved Game State
    public void btnLoad_Game() {
        txta_Logs.appendText("\nLoading Previous Saved Game...");
        GameEngine loaded = GameEngine.loadGame();
        if (loaded != null) {
            this.engine = loaded;
            enableMoveButtons();
            updateGui();
            txta_Logs.appendText("\nGame Loaded Successfully.");
        } else {
            txta_Logs.appendText("\nFailed to load game.");
        }
    }

    /// New Game
    public void btnNewGame (){

        engine = new GameEngine(10);
        txta_Logs.appendText("\n --== New Game == --");
        WelcomePlayer();
        Update_Score();
        Update_Player_Heath();
        disableMoveButtons();
        refreshGui();

    }

    /// HighScore
    public void btnHighScore(){
        FileManager fileManager = new FileManager();
        List<ScoreRecord> topScores = fileManager.getTopScores(5);  // You'll add this method next

        StringBuilder sb = new StringBuilder("Top High Scores:\n");
        int rank = 1;
        for (ScoreRecord score : topScores) {
            sb.append(String.format("%d. %s%n", rank++, score));
        }

        txta_Logs.appendText("\n" + sb.toString());
    }

// </editor-fold>

// <editor-fold desc="Functions">

    /**
      * Button State
     */
    public void enableMoveButtons () {
        move_Down_Button.setDisable(false);
        move_Up_Button.setDisable(false);
        move_Left_Button.setDisable(false);
        move_Right_Button.setDisable(false);

    }

    public void disableMoveButtons () {
        move_Down_Button.setDisable(true);
        move_Up_Button.setDisable(true);
        move_Left_Button.setDisable(true);
        move_Right_Button.setDisable(true);
    }


    /// Player Health
    public void Update_Player_Heath() {
        int currentHealth = engine.getPlayer().getHealth();
        int maxHealth = engine.getPlayer().getMaxHealth();
        txtf_Health.setText("Health: " + currentHealth + " / " + maxHealth);
    }

    /// Player Score
    public void Update_Score() {
        int score = engine.getPlayer().getScore();
        txtf_Score.setText("Score: " + score);
    }
    /// Player Steps
    public void Update_Steps() {
        int steps = engine.getPlayer().getSteps();
        txtf_Steps.setText("Steps: " + steps);

    }

    /// HighScore
    public void Update_HighScore (){

        txtf_HighScore.appendText("HighScore: ");

    }

    /// Welcome the Player
    public void WelcomePlayer (){

        txta_Logs.appendText("\n " +
                "\n Welcome to MiniDungeon." +
                "\n" +
                "\n You Have 100 Steps to Exit the " +
                "\n Dungeon." +
                "\n" +
                "\n Reach the Ladder to Move on" +
                "\n to the Next Level." +
                "\n" +
                "\n Gold are Scattered Everywhere," +
                "\n Avoid Enemies or Fight Them," +
                "\n Beware of Traps Around the Map." +
                "\n" +
                "\n Good Luck." +
                "\n" +
                "\n Now, Choose A Difficulty" +
                "\n Between 1 - 10 And PRESS ENTER BUTTON ^" +
                "\n ");



    }


    /// GameOver
    private void handleGameOver() {


        if (engine.getPlayer().isDead()) {
            txta_Logs.appendText("\nYou Died! Game Over.");
            disableMoveButtons();
            refreshGui();
        } else if (engine.getPlayer().isOutOfSteps()) {
            txta_Logs.appendText("\nYou Have Reached 100 Steps. Game Over.");
            disableMoveButtons();
            refreshGui();
        } else if (engine.getPlayer().isFinished()) {
            txta_Logs.appendText("\nYou reached the ladder. You escaped the dungeon!");
            FileManager fileManager = new FileManager();
            fileManager.addNewScore(engine.getPlayer().getScore());
            fileManager.displayTopScores();
            disableMoveButtons();
            refreshGui();
        }
        refreshGui();
    }



    /// Instructions For the Player
    public void btnHelp_Instructions (){

        txta_Logs.appendText("\n " +
                "\n Welcome to MiniDungeon." +
                "\n" +
                "\n You Have 100 Steps to Exit the " +
                "\n Dungeon." +
                "\n" +
                "\n Reach the Ladder to Move on" +
                "\n to the Next Level." +
                "\n" +
                "\n Gold are Scattered Everywhere," +
                "\n Avoid Enemies or Fight Them," +
                "\n Beware of Traps Around the Map." +
                "\n" +
                "\n Good Luck.");

    }

// </editor-fold>


}
