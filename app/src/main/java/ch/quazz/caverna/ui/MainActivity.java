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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.GamesTable;
import ch.quazz.caverna.R;
import ch.quazz.caverna.games.Game;

public class MainActivity extends Activity {

    public final static String ExtraGameId = "ch.quazz.caverna.GameId";

    private List<Game> games;
    private CavernaDbHelper dbHelper;
    private GamesTable gamesTable;
    private GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (games == null) {
            games = new ArrayList<Game>();
            dbHelper = new CavernaDbHelper(this);
            gamesTable = new GamesTable(dbHelper);
        }

        ListView games = (ListView)findViewById(R.id.games_list);
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

        gamesTable.load(games);
        gamesAdapter = new GamesAdapter(this, games);

        ListView listView = (ListView)findViewById(R.id.games_list);
        listView.setAdapter(gamesAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_game, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.context_game_edit) {
            startGameActivity(info.id);
            return true;
        } else if (item.getItemId() == R.id.context_game_delete) {
            gamesTable.delete(info.id);
            gamesTable.load(games);
            gamesAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void newGame(View view) {
        long id = gamesTable.add(Calendar.getInstance().getTimeInMillis());
        startGameActivity(id);
    }

    private void startGameActivity(long gameId) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ExtraGameId, gameId);
        startActivity(intent);
    }
}
