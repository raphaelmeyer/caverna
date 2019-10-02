package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;
import ch.quazz.caverna.score.Token;

public class FamilyFragment extends PlayerScoreFragment {

    private static final TokenController.Item FamilyItems[] = {
            new TokenController.Item(R.id.dwarfs, Token.DWARFS),
            new TokenController.Item(R.id.dwellings, Token.DWELLINGS)
    };

    private static final TileAdapter.Item[] Dwellings = {
            new TileAdapter.Item(Tile.SimpleDwelling_4_2, R.drawable.simple_dwelling_1),
            new TileAdapter.Item(Tile.SimpleDwelling_3_3, R.drawable.simple_dwelling_2),
            new TileAdapter.Item(Tile.MixedDwelling, R.drawable.mixed_dwelling),
            new TileAdapter.Item(Tile.CoupleDwelling, R.drawable.couple_dwelling),
            new TileAdapter.Item(Tile.AdditionalDwelling, R.drawable.additional_dwelling),
    };

    private final TokenController familyItemController;
    private final TileController dwellingsController;

    public FamilyFragment() {
        familyItemController = new TokenController(FamilyItems);
        dwellingsController = new TileController(Dwellings);
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
        dwellingsController.setup(playerScore, getActivity(), R.id.special_dwellings);
    }
}
