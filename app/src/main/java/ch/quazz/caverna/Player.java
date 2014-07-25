package ch.quazz.caverna;

public class Player {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;

    public Player() {
        family = new Family();
        inventory = new Inventory();
        cattle = new Cattle();
        homeboard = new Homeboard();
    }

    public int score()
    {
        return family.score() + inventory.score() + cattle.score() + homeboard.score();
    }

}
