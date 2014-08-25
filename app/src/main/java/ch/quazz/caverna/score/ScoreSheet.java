package ch.quazz.caverna.score;

import java.util.Map;

public class ScoreSheet {

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
        Assets
    };

    private final Map<Category, Integer> points;

    ScoreSheet(Map<Category, Integer> points) {
        this.points = points;
    }

    public int score(Category category) {
        if (points.containsKey(category)) {
            return points.get(category);
        }
        return 0;
    }

}
