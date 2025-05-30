package dungeon.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileManager {

    private static final String directory_name = "src";
    private static final String file_name = "gameData.txt";

    private List<ScoreRecord> scores = new ArrayList<>();

    public FileManager() {
        ensureDirectoryAndFileExist();
        renderHighScore();
    }

    public List<ScoreRecord> getTopScores(int num) {
        return new ArrayList<>(scores.subList(0, Math.min(num, scores.size())));
    }

    private void ensureDirectoryAndFileExist() {
        File dir = new File(directory_name);
        if (!dir.exists()) {
            boolean createdDir = dir.mkdirs();
            if (createdDir) {
                System.out.println("Resources directory created.");
            } else {
                System.out.println("Failed to create resources directory.");
            }
        }

        File file = new File(dir, file_name);
        if (!file.exists()) {
            try {
                boolean createdFile = file.createNewFile();
                if (createdFile) {
                    System.out.println("HighScores file created inside resources.");
                }
            } catch (IOException e) {
                System.out.println("Failed to create high score file: " + e.getMessage());
            }
        }
    }

    private void renderHighScore() {
        File file = new File(directory_name, file_name);
        if (!file.exists()) {
            System.out.println("HighScores file does not exist...");
            return;
        }

        try (BufferedReader file_reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = file_reader.readLine()) != null) {
                String[] parts = line.split(",");
                int score = Integer.parseInt(parts[0].trim());
                LocalDate date = LocalDate.parse(parts[1].trim());
                scores.add(new ScoreRecord(score, date));
            }
        } catch (Exception e) {
            System.out.println("Loading Score failed: " + e.getMessage());
        }
    }

    public void addNewScore(int score) {
        ScoreRecord newScore = new ScoreRecord(score, LocalDate.now());
        scores.add(newScore);
        Collections.sort(scores);
        saveScoreRecords();
        displayTopScores();
        displayNewRecord(newScore);
    }

    private void saveScoreRecords() {
        File file = new File(directory_name, file_name);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (ScoreRecord score : scores) {
                writer.write(score.getScore() + "," + score.getDate() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error Saving new score Record: " + e.getMessage());
        }
    }

    private void displayNewRecord(ScoreRecord record) {
        int rank = scores.indexOf(record) + 1;
        System.out.println("\nYour Final Score: " + record.getScore() + " (Rank #" + rank + ")");
    }

    public void displayTopScores() {
        System.out.println("\nRanking:");
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            ScoreRecord entry = scores.get(i);
            System.out.printf("%d. %s%n", i + 1, entry);
        }
    }





}//End Bracket
