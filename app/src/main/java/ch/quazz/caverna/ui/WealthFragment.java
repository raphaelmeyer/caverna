package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.R;

public class WealthFragment extends PlayerScoreFragment {

    private final ItemCountController.Item wealthItems[] = {
        new ItemCountController.Item(R.id.dogs, Token.Dogs),
        new ItemCountController.Item(R.id.sheep, Token.Sheep),
        new ItemCountController.Item(R.id.donkeys, Token.Donkeys),
        new ItemCountController.Item(R.id.boars, Token.Boars),
        new ItemCountController.Item(R.id.cattle, Token.Cattle),

        new ItemCountController.Item(R.id.grain, Token.Grains),
        new ItemCountController.Item(R.id.vegetable, Token.Vegetables),
        new ItemCountController.Item(R.id.rubies, Token.Rubies),
        new ItemCountController.Item(R.id.gold, Token.Gold),
        new ItemCountController.Item(R.id.begging_markers, Token.BeggingMarkers),

        new ItemCountController.Item(R.id.small_pastures, Token.SmallPastures),
        new ItemCountController.Item(R.id.large_pastures, Token.LargePastures),
        new ItemCountController.Item(R.id.ore_mines, Token.OreMines),
        new ItemCountController.Item(R.id.ruby_mines, Token.RubyMines),
        new ItemCountController.Item(R.id.unused_tiles, Token.UnusedTiles)
    };
    private final ItemCountController wealthController;

    public WealthFragment() {
        wealthController = new ItemCountController(wealthItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wealth, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        wealthController.setup(playerScore, getActivity());
    }
}
