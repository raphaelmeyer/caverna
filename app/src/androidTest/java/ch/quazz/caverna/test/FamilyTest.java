package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Family;

public class FamilyTest extends AndroidTestCase {

    private Family testee;

    @Override
    protected void setUp() throws Exception {
        testee = new Family();
    }

    public void test_the_initial_family_has_two_dwarfs() {
        assertEquals(2, testee.dwarfs());
    }

    public void test_each_dwarf_scores_a_point() {
        assertEquals(2, testee.score());

        testee.setDwarfs(5);
        assertEquals(5, testee.score());
    }
}
