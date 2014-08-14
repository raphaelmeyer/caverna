package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Furnishing;
import ch.quazz.caverna.score.PlayerScore;

public class CaveFragment extends Fragment {

    final static class FurnishingSelection {
        final Furnishing furnishing;
        final int icon;

        FurnishingSelection(Furnishing furnishing, int icon) {
            this.furnishing = furnishing;
            this.icon = icon;
        }
    }

    private static final FurnishingSelection[] FurnishingsSelection = {
            new FurnishingSelection(Furnishing.Carpenter, R.drawable.carpenter),
            new FurnishingSelection(Furnishing.StoneCarver, R.drawable.stone_carver),
            new FurnishingSelection(Furnishing.Blacksmith, R.drawable.blacksmith),
            new FurnishingSelection(Furnishing.Miner, R.drawable.miner),
            new FurnishingSelection(Furnishing.Builder, R.drawable.builder),
            new FurnishingSelection(Furnishing.Trader, R.drawable.trader),

            new FurnishingSelection(Furnishing.SlaughteringCave, R.drawable.slaughtering_cave),
            new FurnishingSelection(Furnishing.CookingCave, R.drawable.cooking_cave),
            new FurnishingSelection(Furnishing.WorkingCave, R.drawable.working_cave),
            new FurnishingSelection(Furnishing.MiningCave, R.drawable.mining_cave),
            new FurnishingSelection(Furnishing.BreedingCave, R.drawable.breeding_cave),
            new FurnishingSelection(Furnishing.PeacefulCave, R.drawable.peaceful_cave)
    };

    private PlayerScore playerScore;

    public CaveFragment() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
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
