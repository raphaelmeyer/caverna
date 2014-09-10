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

    private final long id;

    private final Map<Token, Integer> itemCount;
    private final Set<Tile> tiles;

    private final List<OnScoreChangeListener> listeners;

    public interface OnScoreChangeListener {
        abstract public void onScoreChanged();
    }

    public PlayerScore(final long id) {
        itemCount = new HashMap<Token, Integer>();
        itemCount.put(Token.Dwarfs, 2);

        tiles = new HashSet<Tile>();

        listeners = new ArrayList<OnScoreChangeListener>();

        this.id = id;
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
        int score = scoreAnimals() + scoreMissingFarmAnimal();
        score += scoreGrain() + scoreVegetable();
        score += scoreRubies();
        score += scoreDwarfs();
        score += scoreUnusedSpace();
        score += scoreTiles();
        score += scoreParlors() + scoreStorages() + scoreChambers();
        score += scoreAssets();

        return score;
    }

    public void addOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnScoreChangeListener(OnScoreChangeListener listener) {
        listeners.remove(listener);
    }

    public ScoreSheet scoreSheet(final int player, final String name) {
        Map<ScoreSheet.Category, Integer> points = new HashMap<ScoreSheet.Category, Integer>();
        points.put(ScoreSheet.Category.Animals, scoreAnimals());
        points.put(ScoreSheet.Category.MissingFarmAnimal, scoreMissingFarmAnimal());
        points.put(ScoreSheet.Category.Grain, scoreGrain());
        points.put(ScoreSheet.Category.Vegetable, scoreVegetable());
        points.put(ScoreSheet.Category.Ruby, scoreRubies());
        points.put(ScoreSheet.Category.Dwarf, scoreDwarfs());
        points.put(ScoreSheet.Category.UnusedSpace, scoreUnusedSpace());
        points.put(ScoreSheet.Category.Tiles, scoreTiles());
        points.put(ScoreSheet.Category.Parlors, scoreParlors());
        points.put(ScoreSheet.Category.Storages, scoreStorages());
        points.put(ScoreSheet.Category.Chambers, scoreChambers());
        points.put(ScoreSheet.Category.Assets, scoreAssets());
        points.put(ScoreSheet.Category.Total, score());
        return new ScoreSheet(id, player, name, points);
    }

    private void notifyScoreChanged() {
        for (OnScoreChangeListener listener : listeners) {
            listener.onScoreChanged();
        }
    }

    private int scoreAnimals() {
        int score = 0;
        for (Token animal : EnumSet.of(Token.Dogs, Token.Sheep, Token.Donkeys, Token.Boars, Token.Cattle)) {
            score += getCount(animal);
        }
        return score;
    }

    private int scoreMissingFarmAnimal() {
        return -missingFarmAnimalCost();
    }

    private int scoreGrain() {
        return (getCount(Token.Grains) + 1) / 2;
    }

    private int scoreVegetable() {
        return getCount(Token.Vegetables);
    }

    private int scoreRubies() {
        return getCount(Token.Rubies);
    }

    private int scoreDwarfs() {
        return getCount(Token.Dwarfs);
    }

    private int scoreUnusedSpace() {
        return -unusedSpaceCost();
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

        score += 3 * getCount(Token.Dwellings);

        return score;
    }

    private int scoreParlors() {
        return 0;
    }

    private int scoreStorages() {
        return 0;
    }

    private int scoreChambers() {
        int score = 0;

        if (has(Tile.WeavingParlor)) {
            score += getCount(Token.Sheep) / 2;
        }

        if (has(Tile.MilkingParlor)) {
            score += getCount(Token.Cattle);
        }

        if (has(Tile.StateParlor)) {
            score += 4 * getCount(Token.AdjacentDwellings);
        }

        if (has(Tile.StoneStorage)) {
            score += getCount(Token.Stone);
        }

        if (has(Tile.OreStorage)) {
            score += getCount(Token.Ore) / 2;
        }

        if (has(Tile.MainStorage)) {
            EnumSet<Tile> yellow = EnumSet.of(
                    Tile.WeavingParlor, Tile.MilkingParlor, Tile.StateParlor,
                    Tile.HuntingParlor, Tile.BeerParlor, Tile.BlacksmithingParlor,

                    Tile.StoneStorage, Tile.OreStorage, Tile.SparePartStorage,
                    Tile.MainStorage, Tile.WeaponStorage, Tile.SuppliesStorage,

                    Tile.BroomChamber, Tile.TreasureChamber, Tile.FoodChamber,
                    Tile.PrayerChamber, Tile.WritingChamber, Tile.FodderChamber
            );
            for (Tile tile : yellow) {
                if (has(tile)) {
                    score += 2;
                }
            }
        }

        if (has(Tile.WeaponStorage)) {
            score += 3 * getCount(Token.Weapons);
        }

        if (has(Tile.SuppliesStorage)) {
            if (getCount(Token.Weapons) == getCount(Token.Dwarfs)) {
                score += 8;
            }
        }

        if (has(Tile.PrayerChamber)) {
            if (getCount(Token.Weapons) == 0) {
                score += 8;
            }
        }

        if (has(Tile.WritingChamber)) {
            score += java.lang.Math.min(7, cost());
        }

        return score;
    }

    private int scoreAssets() {
        return getCount(Token.Gold) - beggingCost();
    }

    private int cost() {
        int cost = missingFarmAnimalCost();
        cost += unusedSpaceCost();
        cost += beggingCost();
        return cost;
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

    private int unusedSpaceCost() {
        return getCount(Token.UnusedSpace);
    }

    private int beggingCost() {
        return 3 * getCount(Token.BeggingMarkers);
    }

}
