package ch.quazz.caverna;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlayerScoreActivity extends Activity {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;
    private Player player;

    private class TabListener implements ActionBar.TabListener {

        private Fragment fragment;

        TabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.player_score_fragment, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_score);

        family = new Family();
        inventory = new Inventory();
        cattle = new Cattle();
        homeboard = new Homeboard();

        player = new Player(family, inventory, cattle, homeboard);

        initTabs();
    }

    private void initTabs() {

        ScoreInventory scoreInventory = new ScoreInventory();
        ScoreLandscape scoreLandscape = new ScoreLandscape();
        ScoreFurnishings scoreFurnishings = new ScoreFurnishings();

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.inventory_tab)
                .setTabListener(new TabListener(scoreInventory)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.landscape_tab)
                .setTabListener(new TabListener(scoreLandscape)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.furnishings_tab)
                .setTabListener(new TabListener(scoreFurnishings)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_ok) {
            return true;
        } else if (id == R.id.action_cancel) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
