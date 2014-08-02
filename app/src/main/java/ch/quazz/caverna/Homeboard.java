package ch.quazz.caverna;

public class Homeboard {

    private int unused = 0;

    private int smallPastures = 0;
    private int largePastures = 0;
    private int oreMines = 0;
    private int rubyMines = 0;

    public int score() {
        return pastureScore() + mineScore();
    }

    public int smallPastures() {
        return smallPastures;
    }

    public void setSmallPastures(int smallPastures) {
        this.smallPastures = smallPastures;
    }

    public int largePastures() {
        return largePastures;
    }

    public void setLargePastures(int largePastures) {
        this.largePastures = largePastures;
    }

    public void setOreMines(int oreMines) {
        this.oreMines = oreMines;
    }

    public void setRubyMines(int rubyMines) {
        this.rubyMines = rubyMines;
    }

    private int pastureScore() {
        return 2 * smallPastures + 4 * largePastures;
    }

    private int mineScore() {
        return 3 * oreMines + 4 * rubyMines;
    }
}
