package ch.quazz.caverna.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerScore {

    private final Map<Item, Integer> itemCount;
    private final List<OnScoreChangeListener> listeners;

    public enum Item {
        Dogs,
        Sheep,
        Donkeys,
        Boars,
        Cattle,

        Dwarfs,

        Grains,
        Vegetables,

        Rubies,
        Gold,
        BeggingMarkers,

        Stone,
        Ore,
        Wood,

        SmallPastures,
        LargePastures,
        OreMines,
        RubyMines
    }

    public interface OnScoreChangeListener {
        abstract public void onScoreChanged();
    }

    public PlayerScore() {
        itemCount = new HashMap<Item, Integer>();
        itemCount.put(Item.Dwarfs, 2);

        listeners = new ArrayList<OnScoreChangeListener>();
    }

    public void setCount(Item item, int count) {
        itemCount.put(item, count);
        for (OnScoreChangeListener listener : listeners) {
            listener.onScoreChanged();
        }
    }

    public int getCount(Item item) {
        if (itemCount.containsKey(item)) {
            return itemCount.get(item);
        }
        return 0;
    }

    public int score()
    {
        return familyScore() + goodsScore() + animalScore() + homeboardScore();
    }

    public void addOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.remove(listener);
    }

    private int familyScore() {
        return getCount(Item.Dwarfs);
    }

    private int animalScore() {
        return getCount(Item.Dogs) + farmAnimalScore();
    }

    private int homeboardScore() {
        return pastureScore() + mineScore();
    }

    private int goodsScore() {
        return grainScore() + getCount(Item.Vegetables) +
                getCount(Item.Rubies) + getCount(Item.Gold) + beggingCost();
    }

    private  int farmAnimalScore() {
        int sum = 0;
        for(Item farmAnimal : new Item[]{ Item.Sheep, Item.Donkeys, Item.Boars, Item.Cattle }) {
            if (getCount(farmAnimal) > 0) {
                sum += getCount(farmAnimal);
            } else {
                sum -= 2;
            }
        }
        return sum;
    }

    private int pastureScore() {
        return 2 * getCount(Item.SmallPastures) + 4 * getCount(Item.LargePastures);
    }

    private int mineScore() {
        return 3 * getCount(Item.OreMines) + 4 * getCount(Item.RubyMines);
    }

    private int beggingCost() {
        return (- getCount(Item.BeggingMarkers) * 3);
    }

    private int grainScore() {
        return (getCount(Item.Grains) + 1) / 2;
    }
}
