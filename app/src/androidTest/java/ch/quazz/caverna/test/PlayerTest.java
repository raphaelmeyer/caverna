package ch.quazz.caverna.test;

import android.test.AndroidTestCase;
import org.mockito.Mockito;

import ch.quazz.caverna.Cattle;
import ch.quazz.caverna.Family;
import ch.quazz.caverna.Homeboard;
import ch.quazz.caverna.Inventory;
import ch.quazz.caverna.Player;

public class PlayerTest extends AndroidTestCase {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;

    private Player testee;

    @Override
    protected void setUp() throws Exception {
        family = Mockito.mock(Family.class);
        inventory = Mockito.mock(Inventory.class);
        cattle = Mockito.mock(Cattle.class);
        homeboard = Mockito.mock(Homeboard.class);

        testee = new Player(family, inventory, cattle, homeboard);
    }

    public void test_calculate_score() {
        Mockito.when(family.score()).thenReturn(3);
        Mockito.when(inventory.score()).thenReturn(20);
        Mockito.when(cattle.score()).thenReturn(100);
        Mockito.when(homeboard.score()).thenReturn(4000);

        assertEquals(4123, testee.score());
    }
}
