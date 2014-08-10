package ch.quazz.caverna.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;

public class WealthFragment extends Fragment {

    private final ItemCountController.ItemCount itemCounts[] = {
        new ItemCountController.ItemCount(R.id.dwarfs, GameItem.Dwarfs),

        new ItemCountController.ItemCount(R.id.dogs, GameItem.Dogs),
        new ItemCountController.ItemCount(R.id.sheep, GameItem.Sheep),
        new ItemCountController.ItemCount(R.id.donkeys, GameItem.Donkeys),
        new ItemCountController.ItemCount(R.id.boars, GameItem.Boars),
        new ItemCountController.ItemCount(R.id.cattle, GameItem.Cattle),

        new ItemCountController.ItemCount(R.id.grain, GameItem.Grains),
        new ItemCountController.ItemCount(R.id.vegetable, GameItem.Vegetables),
        new ItemCountController.ItemCount(R.id.rubies, GameItem.Rubies),
        new ItemCountController.ItemCount(R.id.gold, GameItem.Gold),
        new ItemCountController.ItemCount(R.id.begging_markers, GameItem.BeggingMarkers),

        new ItemCountController.ItemCount(R.id.small_pastures, GameItem.SmallPastures),
        new ItemCountController.ItemCount(R.id.large_pastures, GameItem.LargePastures),
        new ItemCountController.ItemCount(R.id.ore_mines, GameItem.OreMines),
        new ItemCountController.ItemCount(R.id.ruby_mines, GameItem.RubyMines),
        new ItemCountController.ItemCount(R.id.unused_tiles, GameItem.UnusedTiles)
    };

    private PlayerScore playerScore;
    private ItemCountController itemCount;

    public WealthFragment() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wealth, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        itemCount = new ItemCountController(playerScore, itemCounts, getActivity());
    }
}
