package ch.quazz.caverna.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerScore {

    private final Map<GameItem, Integer> itemCount;
    private final List<OnScoreChangeListener> listeners;

    public interface OnScoreChangeListener {
        abstract public void onScoreChanged();
    }

    public PlayerScore() {
        itemCount = new HashMap<GameItem, Integer>();
        itemCount.put(GameItem.Dwarfs, 2);

        listeners = new ArrayList<OnScoreChangeListener>();
    }

    public void setCount(GameItem item, int count) {
        itemCount.put(item, count);
        for (OnScoreChangeListener listener : listeners) {
            listener.onScoreChanged();
        }
    }

    public int getCount(GameItem item) {
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
