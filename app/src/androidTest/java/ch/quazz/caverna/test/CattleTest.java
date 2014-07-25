package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Cattle;

public class CattleTest extends AndroidTestCase {

    private Cattle testee;

    @Override
    protected void setUp() throws Exception {
        testee = new Cattle();
    }

    public void test_each_missing_type_of_farm_animal_costs_two_points() {
        assertEquals(-8, testee.score());
    }

    public void test_each_animal_scores_a_point() {
        testee.setDogs(1);
        testee.setSheep(1);
        testee.setDonkeys(1);
        testee.setBoars(1);
        testee.setCattle(1);
        assertEquals(5, testee.score());
    }
}
