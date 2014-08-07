package ch.quazz.caverna.test;

import android.test.AndroidTestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;


public class PlayerScoreTest extends AndroidTestCase {

    private PlayerScore testee;

    private final int initialScoreDwarfs = 2;
    private final int initialScoreFarmAnimals = -8;
    private final int initialScore = initialScoreDwarfs + initialScoreFarmAnimals;

    @Override
    protected void setUp() throws Exception {
        testee = new PlayerScore();
    }

    public void test_the_initial_score_counts_two_dwarfs() {
        assertThat(testee.getCount(GameItem.Dwarfs), equalTo(2));
    }

    public void test_the_initial_count_of_anything_else_than_dwarfs_is_zero() {
        for (GameItem item : GameItem.values()) {
            if (item != GameItem.Dwarfs) {
                assertThat(testee.getCount(item), equalTo(0));
            }
        }
    }

    public void test_initial_score_is_two_dwarfs_and_missing_farm_animal_cost() {
        assertThat(testee.score(), equalTo(initialScore));
    }

    public void test_each_dwarf_scores_a_point() {
        final int initialScoreWithoutDwarfs = initialScore - initialScoreDwarfs;

        testee.setCount(GameItem.Dwarfs, 3);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 3));

        testee.setCount(GameItem.Dwarfs, 5);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 5));
    }

    public void test_each_missing_type_of_farm_animal_costs_two_points() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        final int farmAnimals = 4;
        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals - (farmAnimals * 2)));
    }

    public void test_each_animal_scores_a_point() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        testee.setCount(GameItem.Dogs, 1);
        testee.setCount(GameItem.Sheep, 1);
        testee.setCount(GameItem.Donkeys, 1);
        testee.setCount(GameItem.Boars, 1);
        testee.setCount(GameItem.Cattle, 1);

        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals + 5));
    }

    public void test_setting_a_farm_animal_count_back_to_zero_readds_the_cost() {
        testee.setCount(GameItem.Sheep, 1);
        testee.setCount(GameItem.Sheep, 0);

        assertThat(testee.score(), equalTo(initialScore));
    }

    public void test_each_small_pasture_scores_two_points() {
        testee.setCount(GameItem.SmallPastures, 1);
        assertScore(2);

        testee.setCount(GameItem.SmallPastures, 3);
        assertScore(6);
    }

    public void test_each_large_pasture_scores_four_points() {
        testee.setCount(GameItem.LargePastures, 1);
        assertScore(4);

        testee.setCount(GameItem.LargePastures, 3);
        assertScore(12);
    }

    public void test_each_ore_mine_scores_three_points() {
        testee.setCount(GameItem.OreMines, 1);
        assertScore(3);

        testee.setCount(GameItem.OreMines, 3);
        assertScore(9);
    }

    public void test_each_ruby_mine_scores_four_points() {
        testee.setCount(GameItem.RubyMines, 1);
        assertScore(4);

        testee.setCount(GameItem.RubyMines, 2);
        assertScore(8);
    }

    public void test_each_pair_of_grains_scores_a_point() {
        testee.setCount(GameItem.Grains, 2);
        assertScore(1);

        testee.setCount(GameItem.Grains, 6);
        assertScore(3);
    }

    public void test_an_odd_number_of_grains_scores_one_more_point() {
        testee.setCount(GameItem.Grains, 1);
        assertScore(1);

        testee.setCount(GameItem.Grains, 7);
        assertScore(4);
    }

    public void test_each_vegetable_scores_a_point() {
        testee.setCount(GameItem.Vegetables, 2);
        assertScore(2);

        testee.setCount(GameItem.Vegetables, 11);
        assertScore(11);
    }

    public void test_each_ruby_scores_a_point() {
        testee.setCount(GameItem.Rubies, 5);
        assertScore(5);
    }

    public void test_each_gold_scores_a_point() {
        testee.setCount(GameItem.Gold, 13);
        assertScore(13);
    }

    public void test_each_begging_marker_costs_three_points() {
        testee.setCount(GameItem.BeggingMarkers, 1);
        assertScore(-3);

        testee.setCount(GameItem.BeggingMarkers, 3);
        assertScore(-9);
    }

    private void assertScore(int score) {
        assertThat(testee.score() - initialScore, equalTo(score));
    }

}
