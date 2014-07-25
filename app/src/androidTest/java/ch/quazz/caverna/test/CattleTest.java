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
}
