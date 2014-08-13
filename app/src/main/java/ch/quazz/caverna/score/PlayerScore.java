package ch.quazz.caverna.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerScore {

    private static final Map<Furnishing, Integer> FurnishingScore = new HashMap<Furnishing, Integer>() {
        {
            put(Furnishing.Dwelling, 3);
            put(Furnishing.SimpleDwelling_4_2, 0);
            put(Furnishing.SimpleDwelling_3_3, 0);
            put(Furnishing.MixedDwelling, 4);
            put(Furnishing.CoupleDwelling, 5);
            put(Furnishing.AdditionalDwelling, 5);

            put(Furnishing.Carpenter, 0);
            put(Furnishing.StoneCarver, 1);
            put(Furnishing.Blacksmith, 3);
            put(Furnishing.Miner, 3);
            put(Furnishing.Builder, 2);
            put(Furnishing.Trader, 2);

            put(Furnishing.SlaughteringCave, 2);
            put(Furnishing.CookingCave, 2);
            put(Furnishing.WorkingCave, 2);
            put(Furnishing.MiningCave, 2);
            put(Furnishing.BreedingCave, 2);
            put(Furnishing.PeacefulCave, 2);
        }
    };

    private final Map<GameItem, Integer> itemCount;
    private final Set<Furnishing> furnishings;

    private final List<OnScoreChangeListener> listeners;

    public interface OnScoreChangeListener {
        abstract public void onScoreChanged();
    }

    public PlayerScore() {
        itemCount = new HashMap<GameItem, Integer>();
        itemCount.put(GameItem.Dwarfs, 2);

        furnishings = new HashSet<Furnishing>();

        listeners = new ArrayList<OnScoreChangeListener>();
    }

    public void setCount(GameItem item, int count) {
        itemCount.put(item, count);
        notifyScoreChanged();
    }

    public int getCount(GameItem item) {
        if (itemCount.containsKey(item)) {
            return itemCount.get(item);
        }
        return 0;
    }

    public void set(Furnishing furnishing) {
        furnishings.add(furnishing);
        notifyScoreChanged();
    }

    public void clear(Furnishing furnishing) {
        furnishings.remove(furnishing);
        notifyScoreChanged();
    }

    public boolean has(Furnishing furnishing) {
        return furnishings.contains(furnishing);
    }

    public int score()
    {
        return familyScore() + goodsScore() + animalScore() + homeboardScore() + dwellingScore();
    }

    public void addOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyScoreChanged() {
        for (OnScoreChangeListener listener : listeners) {
            listener.onScoreChanged();
        }
    }

    private int familyScore() {
        return getCount(GameItem.Dwarfs);
    }

    private int animalScore() {
        return getCount(GameItem.Dogs) + farmAnimalScore();
    }

    private int homeboardScore() {
        return pastureScore() + mineScore() + unusedScore();
    }

    private int goodsScore() {
        return grainScore() + getCount(GameItem.Vegetables) +
                getCount(GameItem.Rubies) + getCount(GameItem.Gold) + beggingCost();
    }

    private int dwellingScore() {
        int score = 0;
        for(Furnishing furnishing : furnishings) {
            score += FurnishingScore.get(furnishing);
        }
        return score;
    }

    private  int farmAnimalScore() {
        int sum = 0;
        for(GameItem farmAnimal : new GameItem[]{ GameItem.Sheep, GameItem.Donkeys, GameItem.Boars, GameItem.Cattle }) {
            if (getCount(farmAnimal) > 0) {
                sum += getCount(farmAnimal);
            } else {
                sum -= 2;
            }
        }
        return sum;
    }

    private int pastureScore() {
        return 2 * getCount(GameItem.SmallPastures) + 4 * getCount(GameItem.LargePastures);
    }

    private int mineScore() {
        return 3 * getCount(GameItem.OreMines) + 4 * getCount(GameItem.RubyMines);
    }

    private int unusedScore() {
        return (- getCount(GameItem.UnusedTiles));
    }

    private int beggingCost() {
        return (- getCount(GameItem.BeggingMarkers) * 3);
    }

    private int grainScore() {
        return (getCount(GameItem.Grains) + 1) / 2;
    }
}
