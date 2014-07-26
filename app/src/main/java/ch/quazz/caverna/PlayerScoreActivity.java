package ch.quazz.caverna;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import org.w3c.dom.Text;

import ch.quazz.caverna.R;

public class PlayerScoreActivity extends Activity {

    private Family family;
    private Inventory inventory;
    private Cattle cattle;
    private Homeboard homeboard;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_score);

        family = new Family();
        inventory = new Inventory();
        cattle = new Cattle();
        homeboard = new Homeboard();

        player = new Player(family, inventory, cattle, homeboard);

        initTabs();
    }

    private void initTabs() {
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("Resources").setIndicator("Resources")
                .setContent(R.id.tab1));

        tabHost.addTab(tabHost.newTabSpec("Landscape").setIndicator("Landscape")
                .setContent(R.id.tab2));

        tabHost.addTab(tabHost.newTabSpec("Furnishing").setIndicator("Furnishing")
                .setContent(R.id.tab3));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.player_score, menu);
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
}
