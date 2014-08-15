package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;

public class CaveFragment extends PlayerScoreFragment {

    private static final FurnishingsAdapter.Selection[] FurnishingsSelection = {
            new FurnishingsAdapter.Selection(Tile.Carpenter, R.drawable.carpenter),
            new FurnishingsAdapter.Selection(Tile.StoneCarver, R.drawable.stone_carver),
            new FurnishingsAdapter.Selection(Tile.Blacksmith, R.drawable.blacksmith),
            new FurnishingsAdapter.Selection(Tile.Miner, R.drawable.miner),
            new FurnishingsAdapter.Selection(Tile.Builder, R.drawable.builder),
            new FurnishingsAdapter.Selection(Tile.Trader, R.drawable.trader),

            new FurnishingsAdapter.Selection(Tile.SlaughteringCave, R.drawable.slaughtering_cave),
            new FurnishingsAdapter.Selection(Tile.CookingCave, R.drawable.cooking_cave),
            new FurnishingsAdapter.Selection(Tile.WorkingCave, R.drawable.working_cave),
            new FurnishingsAdapter.Selection(Tile.MiningCave, R.drawable.mining_cave),
            new FurnishingsAdapter.Selection(Tile.BreedingCave, R.drawable.breeding_cave),
            new FurnishingsAdapter.Selection(Tile.PeacefulCave, R.drawable.peaceful_cave)
    };

    public CaveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cave, container, false);

        GridView gridview = (GridView)view.findViewById(R.id.furnishings);

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

        gridview.setAdapter(new FurnishingsAdapter(getActivity(), check, FurnishingsSelection, listener));

        return view;
    }

}
