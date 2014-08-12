package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
        new FurnishingSelection(Furnishing.Dwelling, R.drawable.additional_dwelling),
        new FurnishingSelection(Furnishing.Dwelling, R.drawable.additional_dwelling),
        new FurnishingSelection(Furnishing.SimpleDwelling_4_2, R.drawable.simple_dwelling_1),
        new FurnishingSelection(Furnishing.SimpleDwelling_3_3, R.drawable.simple_dwelling_2),
        new FurnishingSelection(Furnishing.MixedDwelling, R.drawable.mixed_dwelling),
        new FurnishingSelection(Furnishing.CoupleDwelling, R.drawable.couple_dwelling),
        new FurnishingSelection(Furnishing.AdditionalDwelling, R.drawable.additional_dwelling),

        new FurnishingSelection(Furnishing.Carpenter, R.drawable.carpenter),
        new FurnishingSelection(Furnishing.StoneCarver, R.drawable.stone_carver)
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
        gridview.setAdapter(new FurnishingsAdapter(getActivity(), FurnishingsSelection));

        // move to furnishing selection controller
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkbox = (CheckBox)view;
                if (checkbox != null) {
                    // playerScore.setFurnishing(FurnishingSelection[position].furnishing, checkbox.X());
                }
            }
        });

        return view;
    }
}
