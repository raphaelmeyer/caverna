package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Player;

public class PlayerTest extends AndroidTestCase {

    private Player testee;

    private static final int initial_dwarfs = 2;
    private static final int initial_score = initial_dwarfs;

    @Override
    protected void setUp() throws Exception {
        testee = new Player();
    }

    public void test_the_initial_number_of_dwarfs_is_two() {
        assertEquals(initial_dwarfs, testee.dwarfs());
    }

    public void test_each_dwarf_scores_a_point() {
        testee.setDwarfs(initial_dwarfs + 1);
        assertEquals(initial_score + 1, testee.score());

        testee.setDwarfs(initial_dwarfs + 3);
        assertEquals(initial_score + 3, testee.score());
    }

    public void test_the_initial_number_of_dogs_is_zero() {
        assertEquals(0, testee.dogs());
    }

    public void test_each_dog_scores_a_point() {
        testee.setDogs(1);
        assertEquals(initial_score + 1, testee.score());

        testee.setDogs(4);
        assertEquals(initial_score + 4, testee.score());
    }

    public void test_the_initial_number_of_sheep_is_zero() {
        assertEquals(0, testee.sheep());
    }

}
