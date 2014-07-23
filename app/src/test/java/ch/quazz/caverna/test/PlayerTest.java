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

    public void test() throws Exception {
        assertEquals(testee.score(), 0);
    }
}
