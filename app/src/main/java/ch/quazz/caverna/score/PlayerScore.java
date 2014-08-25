package ch.quazz.caverna.score;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerScore {

    private static final Map<Tile, Integer> FurnishingPoints = new HashMap<Tile, Integer>() {
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

            put(Tile.CuddleRoom, 2);
            put(Tile.BreakfastRoom, 0);
            put(Tile.StubbleRoom, 1);
            put(Tile.WorkRoom, 2);
            put(Tile.GuestRoom, 0);
            put(Tile.OfficeRoom, 0);

            put(Tile.WoodSupplier, 2);
            put(Tile.StoneSupplier, 1);
            put(Tile.RubySupplier, 2);
            put(Tile.DogSchool, 0);
            put(Tile.Quarry, 2);
            put(Tile.Seam, 1);

            put(Tile.HuntingParlor, 1);
            put(Tile.BeerParlor, 3);
            put(Tile.BlacksmithingParlor, 2);
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
        return scoreTokens() + scoreTiles() - cost();
    }

    public void addOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.remove(listener);
    }

    ScoreSheet scoreSheet() {
        return new ScoreSheet(this);
    }

    private void notifyScoreChanged() {
        for (OnScoreChangeListener listener : listeners) {
            listener.onScoreChanged();
        }
    }


    int assetsScore() {
        int assets = getCount(Token.Gold);
        assets -= 3 * getCount(Token.BeggingMarkers);
        return assets;
    }



    private int scoreTokens() {
        int score = getCount(Token.Dwarfs);
        score += 3 * getCount(Token.Dwellings);

        score += animalScore();

        score += (getCount(Token.Grains) + 1) / 2;
        score += getCount(Token.Vegetables);

        score += getCount(Token.Rubies);
        score += getCount(Token.Gold);

        return score;
    }

    private int scoreTiles() {
        int score = 0;

        score += 2 * getCount(Token.SmallPastures);
        score += 4 * getCount(Token.LargePastures);

        score += 3 * getCount(Token.OreMines);
        score += 4 * getCount(Token.RubyMines);

        for (Map.Entry<Tile, Integer> tile: FurnishingPoints.entrySet()) {
            if (has(tile.getKey())) {
                score += tile.getValue();
            }
        }

        score += bonusTiles();

        return score;
    }

    private int cost() {
        int cost = missingFarmAnimalCost();
        cost += getCount(Token.UnusedSpace);
        cost += 3 * getCount(Token.BeggingMarkers);
        return cost;
    }

    private int animalScore() {
        int score = 0;
        for (Token animal : EnumSet.of(Token.Dogs, Token.Sheep, Token.Donkeys, Token.Boars, Token.Cattle)) {
            score += getCount(animal);
        }
        return score;
    }

    private int bonusTiles() {
        int score = 0;
        if (has(Tile.WritingChamber)) {
            score += java.lang.Math.min(7, cost());
        }
        return score;
    }

    private int missingFarmAnimalCost() {
        int cost = 0;
        for (Token farmAnimal : EnumSet.of(Token.Sheep, Token.Donkeys, Token.Boars, Token.Cattle)) {
            if (getCount(farmAnimal) == 0) {
                cost += 2;
            }
        }
        return cost;
    }

}
