package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;

public class BonusFragment extends PlayerScoreFragment {

    private static final TileAdapter.Item BonusTiles[] = {
            new TileAdapter.Item(Tile.WeavingParlor, R.drawable.weaving_parlor),
            new TileAdapter.Item(Tile.MilkingParlor, R.drawable.milking_parlor),
            new TileAdapter.Item(Tile.StateParlor, R.drawable.state_parlor),
            new TileAdapter.Item(Tile.HuntingParlor, R.drawable.hunting_parlor),
            new TileAdapter.Item(Tile.BeerParlor, R.drawable.beer_parlor),
            new TileAdapter.Item(Tile.BlacksmithingParlor, R.drawable.blacksmithing_parlor)
    };

    private TileController bonusController;

    public BonusFragment() {
        bonusController = new TileController(BonusTiles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bonus, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        bonusController.setup(playerScore, getActivity(), R.id.bonus_tiles);
    }
}
