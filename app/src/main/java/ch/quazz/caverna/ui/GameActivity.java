package ch.quazz.caverna.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.quazz.caverna.R;
import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.score.ScoreSheet;

public class GameActivity extends Activity {

    private CavernaDbHelper dbHelper;
    private ScoringPadAdapter scoringPadAdapter;
    private long gameId;

    private static final class Entry {
        final ScoreSheet.Category category;
        final String name;

        Entry(final ScoreSheet.Category category, final String name) {
            this.category = category;
            this.name = name;
        }
    }
    private static final List<Entry> categories = new ArrayList<Entry>() {
        {
            add(new Entry(ScoreSheet.Category.Animals, "Animals"));
            add(new Entry(ScoreSheet.Category.MissingFarmAnimal, "Missing farm animal"));
            add(new Entry(ScoreSheet.Category.Grain, "Grain"));
            add(new Entry(ScoreSheet.Category.Vegetable, "Vegetable"));
            add(new Entry(ScoreSheet.Category.Total, "Total"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameId = intent.getLongExtra(MainActivity.ExtraGameId, 0);

        if (dbHelper == null) {
            dbHelper = new CavernaDbHelper(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
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

    @Override
    protected void onResume() {
        super.onResume();

        List<ScoreSheet> scoringPad = ScoreTable.getScoringPad(dbHelper, gameId);

        //

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        TableLayout table = (TableLayout)findViewById(R.id.game_scoring_pad);

        table.removeAllViews();

        TableRow names = new TableRow(this);
        TextView title = new TextView(this);
        title.setText("Player");
        names.addView(title);
        for (int i = 0; i < scoringPad.size(); i++) {
            TextView name = new TextView(this);
            name.setText(String.valueOf(i + 1));
            name.setGravity(Gravity.CENTER);
            name.setPadding(padding, 0, padding, 0);
            names.addView(name);
        }
        table.addView(names);

        for (Entry entry : categories) {
            TableRow row = new TableRow(this);
            title = new TextView(this);
            title.setText(entry.name);
            row.addView(title);
            for (ScoreSheet sheet : scoringPad) {
                TextView points = new TextView(this);
                points.setGravity(Gravity.RIGHT);
                points.setText(String.valueOf(sheet.score(entry.category)));
                points.setPadding(padding, 0, padding, 0);
                row.addView(points);
            }
            table.addView(row);
        }

        //

        scoringPadAdapter = new ScoringPadAdapter(this);
        scoringPadAdapter.setScoringPad(scoringPad);

        ListView listView = (ListView)findViewById(R.id.game_player_list);
        listView.setAdapter(scoringPadAdapter);

    }

    public void addPlayerScore(View view) {
        // if (players < 7) { ... }
        long scoreId = ScoreTable.addScore(dbHelper, gameId);

        Intent intent = new Intent(this, PlayerScoreActivity.class);
        intent.putExtra(PlayerScoreActivity.ExtraScoreId, scoreId);
        startActivity(intent);
    }
}
