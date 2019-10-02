package ch.quazz.caverna.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;

public class PlayerScoreActivity extends Activity {

    public static final String EXTRA_SCORE_ID = "ch.quazz.caverna.ScoreId";

    private static final String WEALTH = "wealthFragment";
    private static final String FAMILY = "familyFragment";
    private static final String CAVE = "caveFragment";
    private static final String BONUS = "bonusFragment";

    private WealthFragment wealthFragment;
    private FamilyFragment familyFragment;
    private CaveFragment caveFragment;
    private BonusFragment bonusFragment;

    private CavernaDbHelper dbHelper;
    private PlayerScore playerScore;

    private long scoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_score);

        Intent intent = getIntent();
        scoreId = intent.getLongExtra(PlayerScoreActivity.EXTRA_SCORE_ID, 0);

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

        wealthFragment.setPlayerScore(playerScore);
        familyFragment.setPlayerScore(playerScore);
        caveFragment.setPlayerScore(playerScore);
        bonusFragment.setPlayerScore(playerScore);

        updateScore();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getActionBar() != null) {
            outState.putInt("navigation_index", getActionBar().getSelectedNavigationIndex());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
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
                    .setTabListener(new TabListener(R.id.player_score_fragment, wealthFragment, WEALTH)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.family_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, familyFragment, FAMILY)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.cave_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, caveFragment, CAVE)));

            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.bonus_tab)
                    .setTabListener(new TabListener(R.id.player_score_fragment, bonusFragment, BONUS)));
        }
    }

    private void setupTabFragments() {
        if (getFragmentManager() != null) {
            wealthFragment = (WealthFragment)getFragmentManager().findFragmentByTag(WEALTH);
            familyFragment = (FamilyFragment)getFragmentManager().findFragmentByTag(FAMILY);
            caveFragment = (CaveFragment)getFragmentManager().findFragmentByTag(CAVE);
            bonusFragment = (BonusFragment)getFragmentManager().findFragmentByTag(BONUS);
        }

        if (wealthFragment == null) {
            wealthFragment = new WealthFragment();
        }
        if (familyFragment == null) {
            familyFragment = new FamilyFragment();
        }
        if (caveFragment == null) {
            caveFragment = new CaveFragment();
        }
        if (bonusFragment == null) {
            bonusFragment = new BonusFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.player_score_ok:
                finish();
                return true;

            case R.id.player_score_discard:
                ScoreTable.deleteScore(dbHelper, scoreId);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
