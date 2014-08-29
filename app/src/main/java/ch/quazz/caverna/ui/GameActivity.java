package ch.quazz.caverna.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.R;
import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.score.ScoreSheet;

public class GameActivity extends Activity {

    private List<ScoreSheet> scoringPad;
    private CavernaDbHelper dbHelper;
    private ScoringPadAdapter scoringPadAdapter;
    private long gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameId = intent.getLongExtra(MainActivity.ExtraGameId, 0);

        if (scoringPad == null) {
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

        /*
        scoringPadAdapter = new ScoringPadAdapter(this);
        scoringPadAdapter.setScoringPad(scoringPad);

        ListView listView = (ListView)findViewById(R.id.scoring_pad);
        listView.setAdapter(scoringPadAdapter);
        */

        TableLayout table = (TableLayout)findViewById(R.id.scoring_pad);

        TableRow names = new TableRow(this);
        TextView title = new TextView(this);
        title.setText("Names");
        names.addView(title);
        for (int i = 0; i < scoringPad.size(); i++) {
            TextView name = new TextView(this);
            name.setText("name " + i);
            name.setRotation(90.0f);
            names.addView(name);
        }

        TableRow animals = new TableRow(this);
        title = new TextView(this);
        title.setText("Animals");
        animals.addView(title);
        for (ScoreSheet sheet : scoringPad) {
            TextView points = new TextView(this);
            points.setGravity(Gravity.RIGHT);
            points.setText(String.valueOf(sheet.score(ScoreSheet.Category.Animals)));
            animals.addView(points);
        }

        TableRow missingAnimals = new TableRow(this);
        title = new TextView(this);
        title.setText("Missing animals");
        missingAnimals.addView(title);
        for (ScoreSheet sheet : scoringPad) {
            TextView points = new TextView(this);
            points.setGravity(Gravity.RIGHT);
            points.setText(String.valueOf(sheet.score(ScoreSheet.Category.MissingFarmAnimal)));
            missingAnimals.addView(points);
        }

        table.addView(names, new TableLayout.LayoutParams());
        table.addView(animals, new TableLayout.LayoutParams());
        table.addView(missingAnimals, new TableLayout.LayoutParams());

    }

    public void addPlayerScore(View view) {
        long scoreId = ScoreTable.addScore(dbHelper, gameId);

        Intent intent = new Intent(this, PlayerScoreActivity.class);
        intent.putExtra(PlayerScoreActivity.ExtraScoreId, scoreId);
        startActivity(intent);
    }
}
