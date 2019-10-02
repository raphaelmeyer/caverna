package ch.quazz.caverna.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.GamesTable;
import ch.quazz.caverna.R;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.games.Game;

public class MainActivity extends Activity {

    private CavernaDbHelper dbHelper;
    private GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (dbHelper == null) {
            dbHelper = new CavernaDbHelper(this);
        }

        ListView games = findViewById(R.id.games_list);
        registerForContextMenu(games);
        games.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startGameActivity(id);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Game> games = GamesTable.getGames(dbHelper);

        gamesAdapter = new GamesAdapter(this);
        gamesAdapter.setGames(games);

        ListView listView = findViewById(R.id.games_list);
        listView.setAdapter(gamesAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_game, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.context_game_edit:
                startGameActivity(info.id);
                return true;

            case R.id.context_game_delete:
                ScoreTable.deleteScores(dbHelper, info.id);
                GamesTable.deleteGame(dbHelper, info.id);

                List<Game> games = GamesTable.getGames(dbHelper);
                gamesAdapter.setGames(games);

                return true;

            default:
                return false;
        }
    }

    public void newGame(View view) {
        long timestamp = Calendar.getInstance().getTimeInMillis();
        long id = GamesTable.addGame(dbHelper, timestamp);
        startGameActivity(id);
    }

    private void startGameActivity(long gameId) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.ExtraGameId, gameId);
        startActivity(intent);
    }
}
