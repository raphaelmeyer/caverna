package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.Token;

public class FamilyFragment extends PlayerScoreFragment {

    private static final FurnishingsAdapter.Selection[] Dwellings = {
            new FurnishingsAdapter.Selection(Tile.SimpleDwelling_4_2, R.drawable.simple_dwelling_1),
            new FurnishingsAdapter.Selection(Tile.SimpleDwelling_3_3, R.drawable.simple_dwelling_2),
            new FurnishingsAdapter.Selection(Tile.MixedDwelling, R.drawable.mixed_dwelling),
            new FurnishingsAdapter.Selection(Tile.CoupleDwelling, R.drawable.couple_dwelling),
            new FurnishingsAdapter.Selection(Tile.AdditionalDwelling, R.drawable.additional_dwelling),
    };

    private static final ItemCountController.Item FamilyItems[] = {
            new ItemCountController.Item(R.id.dwarfs, Token.Dwarfs),
            new ItemCountController.Item(R.id.dwellings, Token.Dwellings)
    };

    private ItemCountController familyItemController;
    private SelectionController dwellingsController;

    public FamilyFragment() {
        familyItemController = new ItemCountController(FamilyItems);
        dwellingsController = new SelectionController(Dwellings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_family, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        familyItemController.setup(playerScore, getActivity());
        dwellingsController.setup(playerScore, getActivity());
    }
}
