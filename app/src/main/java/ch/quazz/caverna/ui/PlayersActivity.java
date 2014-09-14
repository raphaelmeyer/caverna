package ch.quazz.caverna.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import ch.quazz.caverna.R;
import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.PlayerTable;
import ch.quazz.caverna.data.ScoreTable;
import ch.quazz.caverna.games.Player;

public class PlayersActivity extends Activity {
    final static String ExtraPlayerId = "ch.quazz.caverna.PlayerId";

    private CavernaDbHelper dbHelper;
    private PlayersAdapter playersAdapter;
    private long gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Intent intent = getIntent();
        gameId = intent.getLongExtra(GameActivity.ExtraGameId, 0);

        if (dbHelper == null) {
            dbHelper = new CavernaDbHelper(this);
        }

        ListView games = (ListView)findViewById(R.id.players_list);
        registerForContextMenu(games);
        games.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long scoreId = ScoreTable.addScore(dbHelper, id, gameId);

                Intent intent = new Intent(PlayersActivity.this, PlayerScoreActivity.class);
                intent.putExtra(PlayerScoreActivity.ExtraScoreId, scoreId);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.players_add_player) {
            addPlayerName();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Player> players = PlayerTable.getPlayers(dbHelper);

        playersAdapter = new PlayersAdapter(this);
        playersAdapter.setPlayers(players);

        ListView listView = (ListView)findViewById(R.id.players_list);
        listView.setAdapter(playersAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_players, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.context_players_delete:
                if (ScoreTable.numberOfGames(dbHelper, info.id) == 0) {
                    PlayerTable.deletePlayer(dbHelper, info.id);
                    List<Player> players = PlayerTable.getPlayers(dbHelper);
                    playersAdapter.setPlayers(players);
                }
                break;

            default:
                return false;
        }

        return false;
    }

    private void addPlayerName() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Player name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Name");
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    PlayerTable.addPlayer(dbHelper, name);

                    List<Player> players = PlayerTable.getPlayers(dbHelper);
                    playersAdapter.setPlayers(players);
                }
            }
        });
        builder.show();
    }
}
