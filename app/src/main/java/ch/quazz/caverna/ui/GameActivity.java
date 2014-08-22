package ch.quazz.caverna.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ch.quazz.caverna.R;
import ch.quazz.caverna.data.CavernaDbHelper;
import ch.quazz.caverna.data.PlayerScoreTable;


public class GameActivity extends Activity {

    private CavernaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

    public void addPlayer(View view) {
        PlayerScoreTable playerScoreTable = new PlayerScoreTable(dbHelper);
        playerScoreTable.erase();

        Intent intent = new Intent(this, PlayerScoreActivity.class);
        startActivity(intent);
    }
}
