package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.R;

public class WealthFragment extends PlayerScoreFragment {

    private final TokenController.Item wealthItems[] = {
        new TokenController.Item(R.id.dogs, Token.Dogs),
        new TokenController.Item(R.id.sheep, Token.Sheep),
        new TokenController.Item(R.id.donkeys, Token.Donkeys),
        new TokenController.Item(R.id.boars, Token.Boars),
        new TokenController.Item(R.id.cattle, Token.Cattle),

        new TokenController.Item(R.id.grain, Token.Grains),
        new TokenController.Item(R.id.vegetable, Token.Vegetables),

        new TokenController.Item(R.id.rubies, Token.Rubies),
        new TokenController.Item(R.id.gold, Token.Gold),
        new TokenController.Item(R.id.begging_markers, Token.BeggingMarkers),

        new TokenController.Item(R.id.small_pastures, Token.SmallPastures),
        new TokenController.Item(R.id.large_pastures, Token.LargePastures),
        new TokenController.Item(R.id.ore_mines, Token.OreMines),
        new TokenController.Item(R.id.ruby_mines, Token.RubyMines),
        new TokenController.Item(R.id.unused_tiles, Token.UnusedSpace)
    };
    private final TokenController wealthController;

    public WealthFragment() {
        wealthController = new TokenController(wealthItems);
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
