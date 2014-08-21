package ch.quazz.caverna.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.GamesTable;
import ch.quazz.caverna.data.PlayerScoreTable;
import ch.quazz.caverna.R;
import ch.quazz.caverna.games.Game;
import ch.quazz.caverna.games.Games;

public class MainActivity extends Activity {

    private List<Game> games;
    private CavernaDbHelper dbHelper;
    private GamesTable gamesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (games == null) {
            games = new ArrayList<Game>();
            dbHelper = new CavernaDbHelper(this);
            gamesTable = new GamesTable(dbHelper);
        }

        gamesTable.load(games);

        games.add(new Game(1, "asdf"));
        games.add(new Game(3, "ffff"));

        ListView listView = (ListView)findViewById(R.id.games);
        listView.setAdapter(new GamesAdapter(this, games));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void newGame(View view) {

        // add game to db
        // pass id to game activity

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void addPlayer(View view) {
        CavernaDbHelper dbHelper = new CavernaDbHelper(this);
        PlayerScoreTable playerScoreTable = new PlayerScoreTable(dbHelper);
        playerScoreTable.erase();

        Intent intent = new Intent(this, PlayerScoreActivity.class);
        startActivity(intent);
    }
}
