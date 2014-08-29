package ch.quazz.caverna.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;

public class PlayerScoreActivity extends Activity {

    public final static String ExtraScoreId = "ch.quazz.caverna.ScoreId";

    private static final String Wealth = "wealth";
    private static final String Family = "family";
    private static final String Cave = "cave";
    private static final String Bonus = "bonus";

    private WealthFragment wealth;
    private FamilyFragment family;
    private CaveFragment cave;
    private BonusFragment bonus;

    private CavernaDbHelper dbHelper;
    private PlayerScore playerScore;

    private long scoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_score);

        Intent intent = getIntent();
        scoreId = intent.getLongExtra(PlayerScoreActivity.ExtraScoreId, 0);

        if (dbHelper == null) {
            dbHelper = new CavernaDbHelper(this);
        }

        setupTabFragments();
        setupActionBarTabs();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScoreTable.setScore(dbHelper, playerScore, scoreId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        playerScore = ScoreTable.getScore(dbHelper, scoreId);
        playerScore.addOnScoreChangeListener(new PlayerScore.OnScoreChangeListener() {
            @Override
            public void onScoreChanged() {
                updateScore();
            }
        });

        wealth.setPlayerScore(playerScore);
        family.setPlayerScore(playerScore);
        cave.setPlayerScore(playerScore);
        bonus.setPlayerScore(playerScore);

        updateScore();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getActionBar() != null) {
            outState.putInt("navigation_index", getActionBar().getSelectedNavigationIndex());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt("navigation_index"));
        }
    }

    private void updateScore() {
        String score = String.valueOf(playerScore.score());
        setTitle(getString(R.string.score) + ": " + score);
    }

    private void setupActionBarTabs() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.wealth_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, wealth, Wealth)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.family_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, family, Family)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.cave_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, cave, Cave)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.bonus_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, bonus, Bonus)));
        }
    }

    private void setupTabFragments() {
        if (getFragmentManager() != null) {
            wealth = (WealthFragment)getFragmentManager().findFragmentByTag(Wealth);
            family = (FamilyFragment)getFragmentManager().findFragmentByTag(Family);
            cave = (CaveFragment)getFragmentManager().findFragmentByTag(Cave);
            bonus = (BonusFragment)getFragmentManager().findFragmentByTag(Bonus);
        }

        if (wealth == null) {
            wealth = new WealthFragment();
        }
        if (family == null) {
            family = new FamilyFragment();
        }
        if (cave == null) {
            cave = new CaveFragment();
        }
        if (bonus == null) {
            bonus = new BonusFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_score, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.commit_player_score) {
            return true;
        } else if (id == R.id.discard_player_score) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
