package ch.quazz.caverna.ui;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.PlayerScore;

public class SelectionController {

    private FurnishingsAdapter.Selection options[];

    SelectionController(FurnishingsAdapter.Selection options[]) {
        this.options = options;
    }

    void setup(final PlayerScore playerScore, Activity activity) {
        GridView gridview = (GridView)activity.findViewById(R.id.special_dwellings);

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

        FurnishingsAdapter.Check check = new FurnishingsAdapter.Check() {
            @Override
            public boolean isSelected(Tile tile) {
                return playerScore.has(tile);
            }
        };

        gridview.setAdapter(new FurnishingsAdapter(activity, check, options, listener));
    }
}
