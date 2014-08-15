package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Furnishing;

public class FamilyFragment extends PlayerScoreFragment {

    private static final FurnishingsAdapter.Selection[] Dwellings = {
            new FurnishingsAdapter.Selection(Furnishing.SimpleDwelling_4_2, R.drawable.simple_dwelling_1),
            new FurnishingsAdapter.Selection(Furnishing.SimpleDwelling_3_3, R.drawable.simple_dwelling_2),
            new FurnishingsAdapter.Selection(Furnishing.MixedDwelling, R.drawable.mixed_dwelling),
            new FurnishingsAdapter.Selection(Furnishing.CoupleDwelling, R.drawable.couple_dwelling),
            new FurnishingsAdapter.Selection(Furnishing.AdditionalDwelling, R.drawable.additional_dwelling),
    };

    public FamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family, container, false);

        GridView gridview = (GridView)view.findViewById(R.id.special_dwellings);

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

        gridview.setAdapter(new FurnishingsAdapter(getActivity(), Dwellings, listener, playerScore));

        return view;
    }

}
