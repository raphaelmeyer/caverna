package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Inventory;

public class InventoryTest extends AndroidTestCase {

    private Inventory testee;

    @Override
    protected void setUp() throws Exception {
        testee = new Inventory();
    }

    public void test_initial_inventory_scores_no_points() {
        assertEquals(0, testee.score());
    }

    public void test_each_pair_of_grains_scores_a_point() {
        testee.setGrains(2);
        assertEquals(1, testee.score());

        testee.setGrains(6);
        assertEquals(3, testee.score());
    }

    public void test_an_odd_number_of_grains_scores_one_more_point() {
        testee.setGrains(1);
        assertEquals(1, testee.score());

        testee.setGrains(7);
        assertEquals(4, testee.score());
    }

    public void test_each_vegetable_scores_a_point() {
        testee.setVegetables(2);
        assertEquals(2, testee.score());

        testee.setVegetables(11);
        assertEquals(11, testee.score());
    }

    public void test_each_ruby_scores_a_point() {
        testee.setRubies(12);
        assertEquals(12, testee.score());
    }

    public void test_each_gold_scores_a_point() {
        testee.setGold(13);
        assertEquals(13, testee.score());
    }

    public void test_each_begging_marker_costs_three_points() {
        testee.setBeggingMarkers(1);
        assertEquals(-3, testee.score());

        testee.setBeggingMarkers(3);
        assertEquals(-9, testee.score());
    }
}
