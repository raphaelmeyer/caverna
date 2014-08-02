package ch.quazz.caverna;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlayerScoreActivity extends Activity {

    private ScoreInventory scoreInventory;
    private ScoreLandscape scoreLandscape;
    private ScoreFurnishings scoreFurnishings;

    private final String scoreInventoryTag = "inventory";
    private final String scoreLandscapeTag = "landscape";
    private final String scoreFurnishingsTag = "furnishings";

    private class TabListener implements ActionBar.TabListener {

        private Fragment fragment;
        private String tag;

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

        if (savedInstanceState != null) {
            scoreInventory =
                    (ScoreInventory)getFragmentManager().findFragmentByTag(scoreInventoryTag);
            scoreLandscape =
                    (ScoreLandscape)getFragmentManager().findFragmentByTag(scoreLandscapeTag);
            scoreFurnishings =
                    (ScoreFurnishings)getFragmentManager().findFragmentByTag(scoreFurnishingsTag);
        }

        if (scoreInventory == null) {
            scoreInventory = new ScoreInventory();
        }
        if (scoreLandscape == null) {
            scoreLandscape = new ScoreLandscape();
        }
        if (scoreFurnishings == null) {
            scoreFurnishings = new ScoreFurnishings();
        }

        setupTags();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void updateScore() {
        setTitle("Score: " + Integer.toString(0));
    }

    private void setupTags() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.inventory_tab)
                .setTabListener(new TabListener(scoreInventory, scoreInventoryTag)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.landscape_tab)
                .setTabListener(new TabListener(scoreLandscape, scoreLandscapeTag)));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.furnishings_tab)
                .setTabListener(new TabListener(scoreFurnishings, scoreFurnishingsTag)));
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
