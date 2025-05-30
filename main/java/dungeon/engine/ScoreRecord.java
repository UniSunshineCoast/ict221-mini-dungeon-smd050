package dungeon.engine;

import java.time.LocalDate;


public class ScoreRecord implements Comparable<ScoreRecord> {
    private int score;
    private LocalDate time;

    public ScoreRecord(int score, LocalDate time){
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate () {
        return time;
    }

    @Override
    public int compareTo(ScoreRecord previous) {
        return Integer.compare(previous.score, this.score);
    }

    @Override
    public String toString() {
        return "Score: " + score + ", Date: " + time;
    }
}
