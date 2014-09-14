package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.Tile;

class CaveFragment extends PlayerScoreFragment {

    private static final TileAdapter.Item[] Caves = {
            new TileAdapter.Item(Tile.Carpenter, R.drawable.carpenter),
            new TileAdapter.Item(Tile.StoneCarver, R.drawable.stone_carver),
            new TileAdapter.Item(Tile.Blacksmith, R.drawable.blacksmith),
            new TileAdapter.Item(Tile.Miner, R.drawable.miner),
            new TileAdapter.Item(Tile.Builder, R.drawable.builder),
            new TileAdapter.Item(Tile.Trader, R.drawable.trader),

            new TileAdapter.Item(Tile.SlaughteringCave, R.drawable.slaughtering_cave),
            new TileAdapter.Item(Tile.CookingCave, R.drawable.cooking_cave),
            new TileAdapter.Item(Tile.WorkingCave, R.drawable.working_cave),
            new TileAdapter.Item(Tile.MiningCave, R.drawable.mining_cave),
            new TileAdapter.Item(Tile.BreedingCave, R.drawable.breeding_cave),
            new TileAdapter.Item(Tile.PeacefulCave, R.drawable.peaceful_cave),

            new TileAdapter.Item(Tile.CuddleRoom, R.drawable.cuddle_room),
            new TileAdapter.Item(Tile.BreakfastRoom, R.drawable.breakfast_room),
            new TileAdapter.Item(Tile.StubbleRoom, R.drawable.stubble_room),
            new TileAdapter.Item(Tile.WorkRoom, R.drawable.work_room),
            new TileAdapter.Item(Tile.GuestRoom, R.drawable.guest_room),
            new TileAdapter.Item(Tile.OfficeRoom, R.drawable.office_room),

            new TileAdapter.Item(Tile.WoodSupplier, R.drawable.wood_supplier),
            new TileAdapter.Item(Tile.StoneSupplier, R.drawable.stone_supplier),
            new TileAdapter.Item(Tile.RubySupplier, R.drawable.ruby_supplier),
            new TileAdapter.Item(Tile.DogSchool, R.drawable.dog_school),
            new TileAdapter.Item(Tile.Quarry, R.drawable.quarry),
            new TileAdapter.Item(Tile.Seam, R.drawable.seam)
    };

    private final TileController cavesController;

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
