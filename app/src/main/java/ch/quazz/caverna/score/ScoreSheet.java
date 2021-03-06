package ch.quazz.caverna.score;

import java.util.Map;

public class ScoreSheet {

    public final long id;
    public final int player;
    public final String name;

    public enum Category {
        Animals,
        MissingFarmAnimal,
        Grain,
        Vegetable,
        Ruby,
        Dwarf,
        UnusedSpace,
        Tiles,
        Parlors,
        Storages,
        Chambers,
        Assets,
        Total
    }

    private final Map<Category, Integer> points;

    ScoreSheet(long id, int player, String name, Map<Category, Integer> points) {
        this.id = id;
        this.player = player;
        this.name = name;
        this.points = points;
    }

    public int score(Category category) {
        if (points.containsKey(category)) {
            return points.get(category);
        }
        return 0;
    }

}
