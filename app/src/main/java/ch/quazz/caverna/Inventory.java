package ch.quazz.caverna;

public class Inventory {
    private int gold = 0;
    private int beggingMarkers = 0;

    private int grains = 0;
    private int vegetables = 0;

    private int rubies = 0;

    private int wood = 0;
    private int stone = 0;
    private int ore = 0;

    public int score() {
        return grainsScore() + vegetables + rubies + gold + beggingCost();
    }

    public void setGrains(int grains) {
        this.grains = grains;
    }

    public void setVegetables(int vegetables) {
        this.vegetables = vegetables;
    }

    public void setRubies(int rubies) {
        this.rubies = rubies;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setBeggingMarkers(int beggingMarkers) {
        this.beggingMarkers = beggingMarkers;
    }

    private int grainsScore() {
        return (grains + 1) / 2;
    }

    private int beggingCost() {
        return -3 * beggingMarkers;
    }

}
