package ch.quazz.caverna.test;

import android.test.AndroidTestCase;

import ch.quazz.caverna.Player;

public class PlayerTest extends AndroidTestCase {

    private Player testee;

    @Override
    protected void setUp() throws Exception {
        testee = new Player();
    }

}
