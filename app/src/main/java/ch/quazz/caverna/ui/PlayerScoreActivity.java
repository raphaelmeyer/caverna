package ch.quazz.caverna.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.PlayerScoreTable;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;

public class PlayerScoreActivity extends Activity {

    private WealthFragment wealthFragment;
    private CaveFragment caveFragment;
    private BonusFragment bonusFragment;

    private final String wealthFragmentTag = "wealth";
    private final String caveFragmentTag = "cave";
    private final String bonusFragmentTag = "bonus";

    private PlayerScore playerScore;
    private CavernaDbHelper dbHelper;
    private PlayerScoreTable playerScoreTable;

    private class TabListener implements ActionBar.TabListener {

        private final Fragment fragment;
        private final String tag;

        TabListener(Fragment fragment, String tag) {
            this.fragment = fragment;
            this.tag = tag;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.player_score_fragment, fragment, tag);
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

        // equal to (savedInstanceState == null) ?
        if (playerScore == null) {
            playerScore = new PlayerScore();
            dbHelper = new CavernaDbHelper(this);
            playerScoreTable = new PlayerScoreTable(dbHelper);
        }

        if (savedInstanceState != null) {
            wealthFragment =
                    (WealthFragment)getFragmentManager().findFragmentByTag(wealthFragmentTag);
            caveFragment =
                    (CaveFragment)getFragmentManager().findFragmentByTag(caveFragmentTag);
            bonusFragment =
                    (BonusFragment)getFragmentManager().findFragmentByTag(bonusFragmentTag);
        }

        if (wealthFragment == null) {
            wealthFragment = new WealthFragment();
        }
        if (caveFragment == null) {
            caveFragment = new CaveFragment();
        }
        if (bonusFragment == null) {
            bonusFragment = new BonusFragment();
        }

        wealthFragment.setPlayerScore(playerScore);

        playerScore.addOnScoreChangeListener(new PlayerScore.OnScoreChangeListener() {
            @Override
            public void onScoreChanged() {
                updateScore();
            }
        });

        setupTabs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerScoreTable.save(playerScore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerScoreTable.load(playerScore);
        updateScore();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("navigation_index", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getActionBar().setSelectedNavigationItem(savedInstanceState.getInt("navigation_index"));
    }

    private void updateScore() {
        setTitle("Score: " + Integer.toString(playerScore.score()));
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.wealth_tab)
                .setTabListener(new TabListener(wealthFragment, wealthFragmentTag)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.cave_tab)
                .setTabListener(new TabListener(caveFragment, caveFragmentTag)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.bonus_tab)
                .setTabListener(new TabListener(bonusFragment, bonusFragmentTag)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player_score, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateScore();
        return super.onPrepareOptionsMenu(menu);
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
