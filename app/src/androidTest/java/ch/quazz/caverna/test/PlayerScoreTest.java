package ch.quazz.caverna.test;

import android.test.AndroidTestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import ch.quazz.caverna.PlayerScore;


public class PlayerScoreTest extends AndroidTestCase {

    private PlayerScore testee;

    private int initialScoreDwarfs = 2;
    private int initialScoreFarmAnimals = -8;
    private int initialScore = initialScoreDwarfs + initialScoreFarmAnimals;

    @Override
    protected void setUp() throws Exception {
        testee = new PlayerScore();
    }

    public void test_the_initial_score_counts_two_dwarfs() {
        assertThat(testee.getCount(PlayerScore.Item.Dwarfs), equalTo(2));
    }

    public void test_the_initial_count_of_anything_else_than_dwarfs_is_zero() {
        for (PlayerScore.Item item : PlayerScore.Item.values()) {
            if (item != PlayerScore.Item.Dwarfs) {
                assertThat(testee.getCount(item), equalTo(0));
            }
        }
    }

    public void test_initial_score_is_two_dwarfs_and_missing_farm_animal_cost() {
        assertThat(testee.score(), equalTo(initialScore));
    }

    public void test_each_dwarf_scores_a_point() {
        final int initialScoreWithoutDwarfs = initialScore - initialScoreDwarfs;

        testee.setCount(PlayerScore.Item.Dwarfs, 3);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 3));

        testee.setCount(PlayerScore.Item.Dwarfs, 5);
        assertThat(testee.score(), equalTo(initialScoreWithoutDwarfs + 5));
    }

    public void test_each_missing_type_of_farm_animal_costs_two_points() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        final int farmAnimals = 4;
        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals - (farmAnimals * 2)));
    }

    public void test_each_animal_scores_a_point() {
        final int initialScoreWithoutFarmAnimals = initialScore - initialScoreFarmAnimals;

        testee.setCount(PlayerScore.Item.Dogs, 1);
        testee.setCount(PlayerScore.Item.Sheep, 1);
        testee.setCount(PlayerScore.Item.Donkeys, 1);
        testee.setCount(PlayerScore.Item.Boars, 1);
        testee.setCount(PlayerScore.Item.Cattle, 1);

        assertThat(testee.score(), equalTo(initialScoreWithoutFarmAnimals + 5));
    }

    public void test_setting_a_farm_animal_count_back_to_zero_readds_the_cost() {
        testee.setCount(PlayerScore.Item.Sheep, 1);
        testee.setCount(PlayerScore.Item.Sheep, 0);

        assertThat(testee.score(), equalTo(initialScore));
    }

    public void test_each_small_pasture_scores_two_points() {
        testee.setCount(PlayerScore.Item.SmallPastures, 1);
        assertThat(testee.score(), equalTo(initialScore + 2));

        testee.setCount(PlayerScore.Item.SmallPastures, 3);
        assertThat(testee.score(), equalTo(initialScore + 6));
    }

    public void test_each_large_pasture_scores_four_points() {
        testee.setCount(PlayerScore.Item.LargePastures, 1);
        assertThat(testee.score(), equalTo(initialScore + 4));

        testee.setCount(PlayerScore.Item.LargePastures, 3);
        assertThat(testee.score(), equalTo(initialScore + 12));
    }

    public void test_each_ore_mine_scores_three_points() {
        testee.setCount(PlayerScore.Item.OreMines, 1);
        assertThat(testee.score(), equalTo(initialScore + 3));

        testee.setCount(PlayerScore.Item.OreMines, 3);
        assertThat(testee.score(), equalTo(initialScore + 9));
    }

    public void test_each_ruby_mine_scores_four_points() {
        testee.setCount(PlayerScore.Item.RubyMines, 1);
        assertThat(testee.score(), equalTo(initialScore + 4));

        testee.setCount(PlayerScore.Item.RubyMines, 2);
        assertThat(testee.score(), equalTo(initialScore + 8));
    }

    public void test_each_pair_of_grains_scores_a_point() {
        testee.setCount(PlayerScore.Item.Grains, 2);
        assertThat(testee.score(), equalTo(initialScore + 1));

        testee.setCount(PlayerScore.Item.Grains, 6);
        assertThat(testee.score(), equalTo(initialScore + 3));
    }

    public void test_an_odd_number_of_grains_scores_one_more_point() {
        testee.setCount(PlayerScore.Item.Grains, 1);
        assertThat(testee.score(), equalTo(initialScore + 1));

        testee.setCount(PlayerScore.Item.Grains, 7);
        assertThat(testee.score(), equalTo(initialScore + 4));
    }

    public void test_each_vegetable_scores_a_point() {
        testee.setCount(PlayerScore.Item.Vegetables, 2);
        assertThat(testee.score(), equalTo(initialScore + 2));

        testee.setCount(PlayerScore.Item.Vegetables, 11);
        assertThat(testee.score(), equalTo(initialScore + 11));
    }

    public void test_each_ruby_scores_a_point() {
        testee.setCount(PlayerScore.Item.Rubies, 5);
        assertThat(testee.score(), equalTo(initialScore + 5));
    }

    public void test_each_gold_scores_a_point() {
        testee.setCount(PlayerScore.Item.Gold, 13);
        assertThat(testee.score(), equalTo(initialScore + 13));
    }

    public void test_each_begging_marker_costs_three_points() {
        testee.setCount(PlayerScore.Item.BeggingMarkers, 1);
        assertThat(testee.score(), equalTo(initialScore - 3));

        testee.setCount(PlayerScore.Item.BeggingMarkers, 3);
        assertThat(testee.score(), equalTo(initialScore - 9));
    }

}
