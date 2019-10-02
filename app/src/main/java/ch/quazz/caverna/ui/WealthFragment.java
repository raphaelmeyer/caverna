package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.Token;
import ch.quazz.caverna.R;

public class WealthFragment extends PlayerScoreFragment {

    private static final TokenController.Item[] wealthItems = {
            new TokenController.Item(R.id.dogs, Token.DOGS),
            new TokenController.Item(R.id.sheep, Token.SHEEP),
            new TokenController.Item(R.id.donkeys, Token.DONKEYS),
            new TokenController.Item(R.id.boars, Token.BOARS),
            new TokenController.Item(R.id.cattle, Token.CATTLE),

            new TokenController.Item(R.id.grain, Token.GRAINS),
            new TokenController.Item(R.id.vegetable, Token.VEGETABLES),

            new TokenController.Item(R.id.rubies, Token.RUBIES),
            new TokenController.Item(R.id.gold, Token.GOLD),
            new TokenController.Item(R.id.begging_markers, Token.BEGGING_MARKERS),

            new TokenController.Item(R.id.small_pastures, Token.SMALL_PASTURES),
            new TokenController.Item(R.id.large_pastures, Token.LARGE_PASTURES),
            new TokenController.Item(R.id.ore_mines, Token.ORE_MINES),
            new TokenController.Item(R.id.ruby_mines, Token.RUBY_MINES),
            new TokenController.Item(R.id.unused_tiles, Token.UNUSED_SPACE)
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
