package ch.quazz.caverna;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class PlayerScoreActivity extends Activity {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;
    private Player player;

    private class TabListener<T extends Fragment> implements ActionBar.TabListener {

        private Fragment fragment;

        private final Activity activity;
        private final Class type;

        TabListener(Activity activity, Class type) {
            this.activity = activity;
            this.type = type;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            if(fragment == null) {
                fragment = Fragment.instantiate(activity, type.getName());
                fragmentTransaction.add(R.id.player_score_fragment, fragment);
            } else {
                fragmentTransaction.attach(fragment);
            }

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            if(fragment != null) {
                fragmentTransaction.detach(fragment);
            }
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
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.inventory_tab)
                .setTabListener(new TabListener<ScoreInventory>(this, ScoreInventory.class)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.landscape_tab)
                .setTabListener(new TabListener<ScoreLandscape>(this, ScoreLandscape.class)));
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
