package ch.quazz.caverna.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerScore {

    private static final Map<Tile, Integer> FurnishingScore = new HashMap<Tile, Integer>() {
        {
            put(Tile.SimpleDwelling_4_2, 0);
            put(Tile.SimpleDwelling_3_3, 0);
            put(Tile.MixedDwelling, 4);
            put(Tile.CoupleDwelling, 5);
            put(Tile.AdditionalDwelling, 5);

            put(Tile.Carpenter, 0);
            put(Tile.StoneCarver, 1);
            put(Tile.Blacksmith, 3);
            put(Tile.Miner, 3);
            put(Tile.Builder, 2);
            put(Tile.Trader, 2);

            put(Tile.SlaughteringCave, 2);
            put(Tile.CookingCave, 2);
            put(Tile.WorkingCave, 2);
            put(Tile.MiningCave, 2);
            put(Tile.BreedingCave, 2);
            put(Tile.PeacefulCave, 2);
        }
    };

    private final Map<Token, Integer> itemCount;
    private final Set<Tile> tiles;

    private final List<OnScoreChangeListener> listeners;

    public interface OnScoreChangeListener {
        abstract public void onScoreChanged();
    }

    public PlayerScore() {
        itemCount = new HashMap<Token, Integer>();
        itemCount.put(Token.Dwarfs, 2);

        tiles = new HashSet<Tile>();

        listeners = new ArrayList<OnScoreChangeListener>();
    }

    public void setCount(Token item, int count) {
        itemCount.put(item, count);
        notifyScoreChanged();
    }

    public int getCount(Token item) {
        if (itemCount.containsKey(item)) {
            return itemCount.get(item);
        }
        return 0;
    }

    public void set(Tile tile) {
        tiles.add(tile);
        notifyScoreChanged();
    }

    public void clear(Tile tile) {
        tiles.remove(tile);
        notifyScoreChanged();
    }

    public boolean has(Tile tile) {
        return tiles.contains(tile);
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
        return getCount(Token.Dwarfs);
    }

    private int animalScore() {
        return getCount(Token.Dogs) + farmAnimalScore();
    }

    private int homeboardScore() {
        return pastureScore() + mineScore() + unusedScore();
    }

    private int goodsScore() {
        return grainScore() + getCount(Token.Vegetables) +
                getCount(Token.Rubies) + getCount(Token.Gold) + beggingCost();
    }

    private int dwellingScore() {
        int score = 0;
        for(Tile tile : tiles) {
            if (FurnishingScore.containsKey(tile)) {
                score += FurnishingScore.get(tile);
            }
        }
        return score;
    }

    private  int farmAnimalScore() {
        int sum = 0;
        for(Token farmAnimal : new Token[]{ Token.Sheep, Token.Donkeys, Token.Boars, Token.Cattle }) {
            if (getCount(farmAnimal) > 0) {
                sum += getCount(farmAnimal);
            } else {
                sum -= 2;
            }
        }
        return sum;
    }

    private int pastureScore() {
        return 2 * getCount(Token.SmallPastures) + 4 * getCount(Token.LargePastures);
    }

    private int mineScore() {
        return 3 * getCount(Token.OreMines) + 4 * getCount(Token.RubyMines);
    }

    private int unusedScore() {
        return (- getCount(Token.UnusedTiles));
    }

    private int beggingCost() {
        return (- getCount(Token.BeggingMarkers) * 3);
    }

    private int grainScore() {
        return (getCount(Token.Grains) + 1) / 2;
    }
}
