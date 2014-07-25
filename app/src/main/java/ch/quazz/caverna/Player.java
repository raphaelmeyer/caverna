package ch.quazz.caverna;

public class Player {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;

    public Player(Family family, Inventory inventory, Cattle cattle, Homeboard homeboard) {
        this.family = family;
        this.inventory = inventory;
        this.cattle = cattle;
        this.homeboard = homeboard;

    }

    public int score()
    {
        return family.score() + inventory.score() + cattle.score() + homeboard.score();
    }

}
