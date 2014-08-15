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

    private static final TileAdapter.Selection[] Caves = {
            new TileAdapter.Selection(Tile.Carpenter, R.drawable.carpenter),
            new TileAdapter.Selection(Tile.StoneCarver, R.drawable.stone_carver),
            new TileAdapter.Selection(Tile.Blacksmith, R.drawable.blacksmith),
            new TileAdapter.Selection(Tile.Miner, R.drawable.miner),
            new TileAdapter.Selection(Tile.Builder, R.drawable.builder),
            new TileAdapter.Selection(Tile.Trader, R.drawable.trader),

            new TileAdapter.Selection(Tile.SlaughteringCave, R.drawable.slaughtering_cave),
            new TileAdapter.Selection(Tile.CookingCave, R.drawable.cooking_cave),
            new TileAdapter.Selection(Tile.WorkingCave, R.drawable.working_cave),
            new TileAdapter.Selection(Tile.MiningCave, R.drawable.mining_cave),
            new TileAdapter.Selection(Tile.BreedingCave, R.drawable.breeding_cave),
            new TileAdapter.Selection(Tile.PeacefulCave, R.drawable.peaceful_cave)
    };

    private TileController cavesController;

    public CaveFragment() {
        cavesController = new TileController(Caves);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cave, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        cavesController.setup(playerScore, getActivity(), R.id.furnishings);
    }
}
