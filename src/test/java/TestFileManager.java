
import dungeon.engine.FileManager;
import dungeon.engine.ScoreRecord;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestFileManager {

    private static final String TEST_DIR = "test_resources";
    private static final String TEST_FILE = "gameData.txt";
    private FileManager fileManager;

    @BeforeEach
    void setup() throws IOException {
        // Set up a test directory and empty test file
        Files.createDirectories(Paths.get(TEST_DIR));
        Path testFilePath = Paths.get(TEST_DIR, TEST_FILE);
        Files.deleteIfExists(testFilePath);
        Files.createFile(testFilePath);

        // Create FileManager instance with overridden directory and file names (if possible)
        // And if not possible in current class, consider modifying FileManager to allow setting path for testing
        fileManager = new FileManager() {

            protected String getDirectoryName() {
                return TEST_DIR;
            }


            protected String getFileName() {
                return TEST_FILE;
            }
        };
    }

    @AfterEach
    void cleanup() throws IOException {
        // Delete test directory and contents after each test
        Files.walk(Paths.get(TEST_DIR))
                .map(Path::toFile)
                .forEach(File::delete);
        Files.deleteIfExists(Paths.get(TEST_DIR));
    }

    @Test
    void testAddNewScore_andRetrieveTopScores() {
        fileManager.addNewScore(50);
        fileManager.addNewScore(70);
        fileManager.addNewScore(60);

        List<ScoreRecord> topScores = fileManager.getTopScores(2);

        assertEquals(2, topScores.size());
        assertEquals(70, topScores.get(0).getScore());
        assertEquals(60, topScores.get(1).getScore());
    }

    @Test
    void testScoresAreSorted() {
        fileManager.addNewScore(10);
        fileManager.addNewScore(30);
        fileManager.addNewScore(20);

        List<ScoreRecord> allScores = fileManager.getTopScores(10);

        assertEquals(3, allScores.size());
        assertEquals(30, allScores.get(0).getScore());
        assertEquals(20, allScores.get(1).getScore());
        assertEquals(10, allScores.get(2).getScore());
    }

    @Test
    void testLoadingScoresFromFile() throws IOException {
        // Prepare file with score data
        Path filePath = Paths.get(TEST_DIR, TEST_FILE);
        Files.write(filePath, List.of("100," + LocalDate.now(), "80," + LocalDate.now()));

        // Reinitialize FileManager to load the file
        fileManager = new FileManager() {

            protected String getDirectoryName() {
                return TEST_DIR;
            }

            protected String getFileName() {
                return TEST_FILE;
            }
        };

        List<ScoreRecord> scores = fileManager.getTopScores(5);

        assertEquals(2, scores.size());
        assertEquals(100, scores.get(0).getScore());
        assertEquals(80, scores.get(1).getScore());
    }
}
