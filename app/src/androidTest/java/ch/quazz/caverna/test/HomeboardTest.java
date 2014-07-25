package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Homeboard;

public class HomeboardTest extends AndroidTestCase {

    private Homeboard testee;

    @Override
    protected void setUp() throws Exception {
        testee = new Homeboard();
    }

    public void test_each_small_pasture_scores_two_points() {
        testee.setSmallPastures(1);
        assertEquals(2, testee.score());

        testee.setSmallPastures(3);
        assertEquals(6, testee.score());
    }

    public void test_each_large_pasture_scores_four_points() {
        testee.setLargePastures(1);
        assertEquals(4, testee.score());

        testee.setLargePastures(3);
        assertEquals(12, testee.score());
    }

    public void test_each_ore_mine_scores_three_points() {
        testee.setOreMines(1);
        assertEquals(3, testee.score());

        testee.setOreMines(3);
        assertEquals(9, testee.score());
    }

    public void test_each_ruby_mine_scores_four_points() {
        testee.setRubyMines(1);
        assertEquals(4, testee.score());

        testee.setRubyMines(2);
        assertEquals(8, testee.score());
    }
}
