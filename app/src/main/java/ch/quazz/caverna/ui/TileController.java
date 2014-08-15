package ch.quazz.caverna.ui;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.PlayerScore;

public class TileController {

    private TileAdapter.Selection options[];

    TileController(TileAdapter.Selection options[]) {
        this.options = options;
    }

    void setup(final PlayerScore playerScore, Activity activity, int gridId) {
        GridView gridview = (GridView)activity.findViewById(gridId);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tile tile = (Tile)(buttonView.getTag());
                if (isChecked) {
                    playerScore.set(tile);
                } else {
                    playerScore.clear(tile);
                }
            }
        };

        TileAdapter.Check check = new TileAdapter.Check() {
            @Override
            public boolean isSelected(Tile tile) {
                return playerScore.has(tile);
            }
        };

        gridview.setAdapter(new TileAdapter(activity, check, options, listener));
    }
}
