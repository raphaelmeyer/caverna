package ch.quazz.caverna;

import org.junit.Before;
import org.junit.Test;

import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.Token;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayerScoreTest {

    private PlayerScore testee;

    private final int initialScoreDwarfs = 2;
    private final int initialScoreFarmAnimals = -8;
    private final int initialScore = initialScoreDwarfs + initialScoreFarmAnimals;

    @Before
    public void setUp() {
        testee = new PlayerScore(0);
    }

    @Test
    public void test_the_initial_score_counts_two_dwarfs() {
        assertThat(testee.getCount(Token.Dwarfs), equalTo(2));
    }

    @Test
    public void test_the_initial_count_of_anything_else_than_dwarfs_is_zero() {
        for (Token item : Token.values()) {
            if (item != Token.Dwarfs) {
                assertThat(testee.getCount(item), equalTo(0));
            }
        }
    }

    @Test
    public void test_initial_score_is_two_dwarfs_and_missing_farm_animal_cost() {
        assertThat(testee.score(), equalTo(initialScore));
    }

    @Test
    public void test_each_dwarf_scores_a_point() {
        final int initialScoreWithoutDwarfs = initialScore - initialScoreDwarfs;

        testee.setCount(Token.Dwarfs, 3);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 3));

        testee.setCount(Token.Dwarfs, 5);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 5));
    }

    @Test
    public void test_each_missing_type_of_farm_animal_costs_two_points() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        final int farmAnimals = 4;
        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals - (farmAnimals * 2)));
    }

    @Test
    public void test_each_animal_scores_a_point() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        testee.setCount(Token.Dogs, 1);
        testee.setCount(Token.Sheep, 1);
        testee.setCount(Token.Donkeys, 1);
        testee.setCount(Token.Boars, 1);
        testee.setCount(Token.Cattle, 1);

        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals + 5));
    }

    @Test
    public void test_setting_a_farm_animal_count_back_to_zero_re_adds_the_cost() {
        testee.setCount(Token.Sheep, 1);
        testee.setCount(Token.Sheep, 0);

        assertThat(testee.score(), equalTo(initialScore));
    }

    @Test
    public void test_each_small_pasture_scores_two_points() {
        testee.setCount(Token.SmallPastures, 1);
        assertScore(2);

        testee.setCount(Token.SmallPastures, 3);
        assertScore(6);
    }

    @Test
    public void test_each_large_pasture_scores_four_points() {
        testee.setCount(Token.LargePastures, 1);
        assertScore(4);

        testee.setCount(Token.LargePastures, 3);
        assertScore(12);
    }

    @Test
    public void test_each_ore_mine_scores_three_points() {
        testee.setCount(Token.OreMines, 1);
        assertScore(3);

        testee.setCount(Token.OreMines, 3);
        assertScore(9);
    }

    @Test
    public void test_each_ruby_mine_scores_four_points() {
        testee.setCount(Token.RubyMines, 1);
        assertScore(4);

        testee.setCount(Token.RubyMines, 2);
        assertScore(8);
    }

    @Test
    public void test_each_pair_of_grains_scores_a_point() {
        testee.setCount(Token.Grains, 2);
        assertScore(1);

        testee.setCount(Token.Grains, 6);
        assertScore(3);
    }

    @Test
    public void test_an_odd_number_of_grains_scores_one_more_point() {
        testee.setCount(Token.Grains, 1);
        assertScore(1);

        testee.setCount(Token.Grains, 7);
        assertScore(4);
    }

    @Test
    public void test_each_vegetable_scores_a_point() {
        testee.setCount(Token.Vegetables, 2);
        assertScore(2);

        testee.setCount(Token.Vegetables, 11);
        assertScore(11);
    }

    @Test
    public void test_each_ruby_scores_a_point() {
        testee.setCount(Token.Rubies, 5);
        assertScore(5);
    }

    @Test
    public void test_each_gold_scores_a_point() {
        testee.setCount(Token.Gold, 13);
        assertScore(13);
    }

    @Test
    public void test_each_begging_marker_costs_three_points() {
        testee.setCount(Token.BeggingMarkers, 1);
        assertScore(-3);

        testee.setCount(Token.BeggingMarkers, 3);
        assertScore(-9);
    }

    @Test
    public void test_each_unused_space_costs_one_point() {
        testee.setCount(Token.UnusedSpace, 1);
        assertScore(-1);

        testee.setCount(Token.UnusedSpace, 4);
        assertScore(-4);
    }

    @Test
    public void test_each_dwelling_scores_3_points() {
        testee.setCount(Token.Dwellings, 1);
        assertScore(3);

        testee.setCount(Token.Dwellings, 3);
        assertScore(9);
    }

    @Test
    public void test_furnishing_tiles_score_given_number_of_points() {
        testee.set(Tile.CoupleDwelling);
        assertScore(5);
        testee.clear(Tile.CoupleDwelling);

        testee.set(Tile.Miner);
        assertScore(3);
        testee.clear(Tile.Miner);

        testee.set(Tile.CookingCave);
        assertScore(2);
        testee.clear(Tile.CookingCave);

        testee.set(Tile.OfficeRoom);
        assertScore(0);
        testee.clear(Tile.OfficeRoom);

        testee.set(Tile.WoodSupplier);
        assertScore(2);
        testee.clear(Tile.WoodSupplier);

        testee.set(Tile.HuntingParlor);
        assertScore(1);
        testee.clear(Tile.HuntingParlor);
    }

    @Test
    public void test_weaving_parlor_scores_a_bonus_point_for_each_pair_of_sheep() {
        assertBonus(Tile.WeavingParlor, 0);

        testee.setCount(Token.Sheep, 1);
        assertBonus(Tile.WeavingParlor, 0);

        testee.setCount(Token.Sheep, 2);
        assertBonus(Tile.WeavingParlor, 1);

        testee.setCount(Token.Sheep, 3);
        assertBonus(Tile.WeavingParlor, 1);

        testee.setCount(Token.Sheep, 8);
        assertBonus(Tile.WeavingParlor, 4);
    }

    @Test
    public void test_milking_parlor_scores_a_bonus_point_for_each_cattle() {
        assertBonus(Tile.MilkingParlor, 0);

        testee.setCount(Token.Cattle, 1);
        assertBonus(Tile.MilkingParlor, 1);

        testee.setCount(Token.Cattle, 2);
        assertBonus(Tile.MilkingParlor, 2);

        testee.setCount(Token.Cattle, 5);
        assertBonus(Tile.MilkingParlor, 5);
    }

    @Test
    public void test_state_parlor_scores_four_bonus_points_for_each_adjacent_dwelling() {
        assertBonus(Tile.StateParlor, 0);

        testee.setCount(Token.AdjacentDwellings, 1);
        assertBonus(Tile.StateParlor, 4);

        testee.setCount(Token.AdjacentDwellings, 4);
        assertBonus(Tile.StateParlor, 16);
    }

    @Test
    public void test_stone_storage_scores_a_bonus_point_for_each_stone() {
        assertBonus(Tile.StoneStorage, 0);

        testee.setCount(Token.Stone, 1);
        assertBonus(Tile.StoneStorage, 1);

        testee.setCount(Token.Stone, 6);
        assertBonus(Tile.StoneStorage, 6);
    }

    @Test
    public void test_ore_storage_scores_a_bonus_point_for_each_pair_of_ore() {
        assertBonus(Tile.OreStorage, 0);

        testee.setCount(Token.Ore, 5);
        assertBonus(Tile.OreStorage, 2);

        testee.setCount(Token.Ore, 10);
        assertBonus(Tile.OreStorage, 5);
    }

    @Test
    public void test_main_storage_scores_two_bonus_points_for_each_yellow_tile() {
        assertBonus(Tile.MainStorage, 2);

        testee.set(Tile.WeavingParlor);
        assertBonus(Tile.MainStorage, 4);

        testee.set(Tile.OreStorage);
        assertBonus(Tile.MainStorage, 6);

        testee.set(Tile.FoodChamber);
        assertBonus(Tile.MainStorage, 8);
    }

    @Test
    public void test_weapon_storage_scores_three_bonus_points_for_each_armed_dwarf() {
        assertBonus(Tile.WeaponStorage, 0);

        testee.setCount(Token.Weapons, 1);
        assertBonus(Tile.WeaponStorage, 3);

        testee.setCount(Token.Weapons, 4);
        assertBonus(Tile.WeaponStorage, 12);
    }

    @Test
    public void test_supplies_storage_scores_eight_points_if_all_dwarfs_are_armed() {
        assertBonus(Tile.SuppliesStorage, 0);

        testee.setCount(Token.Weapons, 1);
        assertBonus(Tile.SuppliesStorage, 0);

        testee.setCount(Token.Weapons, 2);
        assertBonus(Tile.SuppliesStorage, 8);

        testee.setCount(Token.Dwarfs, 4);
        testee.setCount(Token.Weapons, 3);
        assertBonus(Tile.SuppliesStorage, 0);

        testee.setCount(Token.Dwarfs, 4);
        testee.setCount(Token.Weapons, 4);
        assertBonus(Tile.SuppliesStorage, 8);
    }

    @Test
    public void test_broom_chamber_scores_no_points_for_four_dwarfs_or_less() {
        assertBonus(Tile.BroomChamber, 0);

        testee.setCount(Token.Dwarfs, 3);
        assertBonus(Tile.BroomChamber, 0);

        testee.setCount(Token.Dwarfs, 4);
        assertBonus(Tile.BroomChamber, 0);
    }

    @Test
    public void test_broom_chamber_scores_five_points_for_five_dwarfs() {
        testee.setCount(Token.Dwarfs, 5);
        assertBonus(Tile.BroomChamber, 5);
    }

    @Test
    public void test_broom_chamber_scores_ten_points_for_six_dwarfs() {
        testee.setCount(Token.Dwarfs, 6);
        assertBonus(Tile.BroomChamber, 10);
    }

    @Test
    public void test_treasure_chamber_scores_an_additional_point_for_each_ruby() {
        assertBonus(Tile.TreasureChamber, 0);

        testee.setCount(Token.Rubies, 1);
        assertBonus(Tile.TreasureChamber, 1);

        testee.setCount(Token.Rubies, 5);
        assertBonus(Tile.TreasureChamber, 5);
    }

    @Test
    public void test_food_chamber_scores_two_points_for_each_set_of_grain_and_vegetable() {
        assertBonus(Tile.FoodChamber, 0);

        testee.setCount(Token.Grains, 0);
        testee.setCount(Token.Vegetables, 1);
        assertBonus(Tile.FoodChamber, 0);

        testee.setCount(Token.Grains, 1);
        testee.setCount(Token.Vegetables, 0);
        assertBonus(Tile.FoodChamber, 0);

        testee.setCount(Token.Grains, 1);
        testee.setCount(Token.Vegetables, 1);
        assertBonus(Tile.FoodChamber, 2);

        testee.setCount(Token.Grains, 3);
        testee.setCount(Token.Vegetables, 4);
        assertBonus(Tile.FoodChamber, 6);

        testee.setCount(Token.Grains, 5);
        testee.setCount(Token.Vegetables, 2);
        assertBonus(Tile.FoodChamber, 4);
    }

    @Test
    public void test_prayer_chamber_scores_eight_points_if_no_dwarfs_are_armed() {
        assertBonus(Tile.PrayerChamber, 8);

        testee.setCount(Token.Weapons, 1);
        assertBonus(Tile.PrayerChamber, 0);

        testee.setCount(Token.Dwarfs, 5);

        testee.setCount(Token.Weapons, 0);
        assertBonus(Tile.PrayerChamber, 8);

        testee.setCount(Token.Weapons, 3);
        assertBonus(Tile.PrayerChamber, 0);
    }

    @Test
    public void test_writing_chamber_prevents_up_to_7_negative_points() {
        testee.set(Tile.WritingChamber);

        testee.setCount(Token.Dwarfs, 2);           // +2
        testee.setCount(Token.UnusedSpace, 1);      // -1
        testee.setCount(Token.Sheep, 0);            // -2
        testee.setCount(Token.Donkeys, 0);          // -2
        testee.setCount(Token.Boars, 0);            // -2
        testee.setCount(Token.Cattle, 0);           // -2
        testee.setCount(Token.BeggingMarkers, 1);   // -3

        assertThat(testee.score(), equalTo(2 + -12 + 7));

        testee.setCount(Token.Cattle, 1);           // + 1 - (-2)
        assertThat(testee.score(), equalTo(3 + -10 + 7));

        testee.setCount(Token.Donkeys, 1);          // + 1 - (-2)
        assertThat(testee.score(), equalTo(4 + -8 + 7));

        testee.setCount(Token.UnusedSpace, 0);      // + 0 - (-1)
        assertThat(testee.score(), equalTo(4 + -7 + 7));

        testee.setCount(Token.BeggingMarkers, 0);   // + 0 - (-3)
        assertThat(testee.score(), equalTo(4 + -4 + 4));

        testee.setCount(Token.Sheep, 3);            // + 3 - (-2)
        assertThat(testee.score(), equalTo(7 + -2 + 2));

        testee.setCount(Token.Boars, 2);            // + 2 - (-2)
        assertThat(testee.score(), equalTo(9 + -0 + 0));
    }

    @Test
    public void test_fodder_chamber_scores_one_point_for_every_three_farm_animals() {
        testee.setCount(Token.Dogs, 7);
        assertBonus(Tile.FodderChamber, 0);

        testee.setCount(Token.Sheep, 4);
        assertBonus(Tile.FodderChamber, 1);

        testee.setCount(Token.Cattle, 4);
        assertBonus(Tile.FodderChamber, 2);

        testee.setCount(Token.Donkeys, 4);
        assertBonus(Tile.FodderChamber, 4);

        testee.setCount(Token.Boars, 4);
        assertBonus(Tile.FodderChamber, 5);
    }

    private void assertScore(int score) {
        assertThat(testee.score() - initialScore, equalTo(score));
    }

    private void assertBonus(Tile tile, int bonus) {
        testee.clear(tile);
        int normalScore = testee.score();

        testee.set(tile);
        assertThat(testee.score() - normalScore, equalTo(bonus));
    }


}
