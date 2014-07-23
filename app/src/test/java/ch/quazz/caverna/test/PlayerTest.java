package ch.quazz.caverna.test;

import android.test.InstrumentationTestCase;

import ch.quazz.caverna.Player;

public class PlayerTest extends InstrumentationTestCase {

    private Player testee;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testee = new Player();
    }

    public void test_each_dwarf_counts_a_point() throws  Exception {
        testee.setDwarfs(2);
        assertEquals(2, testee.score());

        testee.setDwarfs(5);
        assertEquals(5, testee.score());
    }

    public void test_each_animal_scores_a_point() {
        testee.setDogs(1);
        testee.setSheep(1);
        testee.setDonkey(1);
        testee.setCow(1);
        testee.setRoar(1);

        assertEquals(5, testee.score());
    }

    public void test_each_missing_NUTZTIER_scores_two_minus_points() {
        assertEquals(-8, testee.score());
    }
}
