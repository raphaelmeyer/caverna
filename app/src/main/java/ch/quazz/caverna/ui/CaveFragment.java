package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Furnishing;

public class CaveFragment extends PlayerScoreFragment {

    private static final FurnishingsAdapter.Selection[] FurnishingsSelection = {
            new FurnishingsAdapter.Selection(Furnishing.Carpenter, R.drawable.carpenter),
            new FurnishingsAdapter.Selection(Furnishing.StoneCarver, R.drawable.stone_carver),
            new FurnishingsAdapter.Selection(Furnishing.Blacksmith, R.drawable.blacksmith),
            new FurnishingsAdapter.Selection(Furnishing.Miner, R.drawable.miner),
            new FurnishingsAdapter.Selection(Furnishing.Builder, R.drawable.builder),
            new FurnishingsAdapter.Selection(Furnishing.Trader, R.drawable.trader),

            new FurnishingsAdapter.Selection(Furnishing.SlaughteringCave, R.drawable.slaughtering_cave),
            new FurnishingsAdapter.Selection(Furnishing.CookingCave, R.drawable.cooking_cave),
            new FurnishingsAdapter.Selection(Furnishing.WorkingCave, R.drawable.working_cave),
            new FurnishingsAdapter.Selection(Furnishing.MiningCave, R.drawable.mining_cave),
            new FurnishingsAdapter.Selection(Furnishing.BreedingCave, R.drawable.breeding_cave),
            new FurnishingsAdapter.Selection(Furnishing.PeacefulCave, R.drawable.peaceful_cave)
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
                Furnishing furnishing = (Furnishing)(buttonView.getTag());
                if (isChecked) {
                    playerScore.set(furnishing);
                } else {
                    playerScore.clear(furnishing);
                }
            }
        };

        gridview.setAdapter(new FurnishingsAdapter(getActivity(), FurnishingsSelection, listener, playerScore));

        return view;
    }

}
