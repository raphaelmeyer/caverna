package ch.quazz.caverna.score;

public class ScoreSheet {

    private final PlayerScore score;

    ScoreSheet(PlayerScore score) {
        this.score = score;
    }

    public int animals() {
        return 0;
    }

    // ...

    public int assets() {
        return score.assetsScore();
    }
}
