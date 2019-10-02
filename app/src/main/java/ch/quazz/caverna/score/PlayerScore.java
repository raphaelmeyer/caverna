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
        itemCount.put(Token.DWARFS, 2);

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
        for (Token animal : EnumSet.of(Token.DOGS, Token.SHEEP, Token.DONKEYS, Token.BOARS, Token.CATTLE)) {
            score += getCount(animal);
        }
        return score;
    }

    private int scoreMissingFarmAnimal() {
        return -missingFarmAnimalCost();
    }

    private int scoreGrain() {
        return (getCount(Token.GRAINS) + 1) / 2;
    }

    private int scoreVegetable() {
        return getCount(Token.VEGETABLES);
    }

    private int scoreRubies() {
        return getCount(Token.RUBIES);
    }

    private int scoreDwarfs() {
        return getCount(Token.DWARFS);
    }

    private int scoreUnusedSpace() {
        return -unusedSpaceCost();
    }

    private int scoreTiles() {
        int score = 0;

        score += 2 * getCount(Token.SMALL_PASTURES);
        score += 4 * getCount(Token.LARGE_PASTURES);

        score += 3 * getCount(Token.ORE_MINES);
        score += 4 * getCount(Token.RUBY_MINES);

        for (Map.Entry<Tile, Integer> tile: FurnishingPoints.entrySet()) {
            if (has(tile.getKey())) {
                score += tile.getValue();
            }
        }

        score += 3 * getCount(Token.DWELLINGS);

        return score;
    }

    private int scoreParlors() {
        int score = 0;

        if (has(Tile.WeavingParlor)) {
            score += getCount(Token.SHEEP) / 2;
        }

        if (has(Tile.MilkingParlor)) {
            score += getCount(Token.CATTLE);
        }

        if (has(Tile.StateParlor)) {
            score += 4 * getCount(Token.ADJACENT_DWELLINGS);
        }

        return score;
    }

    private int scoreStorages()
    {
        int score = 0;

        if (has(Tile.StoneStorage)) {
            score += getCount(Token.STONE);
        }

        if (has(Tile.OreStorage)) {
            score += getCount(Token.ORE) / 2;
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
            score += 3 * getCount(Token.WEAPONS);
        }

        if (has(Tile.SuppliesStorage)) {
            if (getCount(Token.WEAPONS) == getCount(Token.DWARFS)) {
                score += 8;
            }
        }

        return score;
    }

    private int scoreChambers() {
        int score = 0;

        if (has(Tile.BroomChamber)) {
            if (getCount(Token.DWARFS) == 5) {
                score += 5;
            } else if (getCount(Token.DWARFS) == 6) {
                score += 10;
            }
        }

        if (has(Tile.TreasureChamber)) {
            score += getCount(Token.RUBIES);
        }

        if (has(Tile.FoodChamber)) {
            int sets = Math.min(getCount(Token.GRAINS), getCount(Token.VEGETABLES));
            score += 2 * sets;
        }

        if (has(Tile.PrayerChamber)) {
            if (getCount(Token.WEAPONS) == 0) {
                score += 8;
            }
        }

        if (has(Tile.WritingChamber)) {
            score += Math.min(7, cost());
        }

        if (has(Tile.FodderChamber)) {
            int farmAnimals = 0;
            for (Token farmAnimal : EnumSet.of(Token.SHEEP, Token.DONKEYS, Token.BOARS, Token.CATTLE)) {
                farmAnimals += getCount(farmAnimal);
            }
            score += farmAnimals / 3;
        }

        return score;
    }

    private int scoreAssets() {
        return getCount(Token.GOLD) - beggingCost();
    }

    private int cost() {
        int cost = missingFarmAnimalCost();
        cost += unusedSpaceCost();
        cost += beggingCost();
        return cost;
    }

    private int missingFarmAnimalCost() {
        int cost = 0;
        for (Token farmAnimal : EnumSet.of(Token.SHEEP, Token.DONKEYS, Token.BOARS, Token.CATTLE)) {
            if (getCount(farmAnimal) == 0) {
                cost += 2;
            }
        }
        return cost;
    }

    private int unusedSpaceCost() {
        return getCount(Token.UNUSED_SPACE);
    }

    private int beggingCost() {
        return 3 * getCount(Token.BEGGING_MARKERS);
    }

}
