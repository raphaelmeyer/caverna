package ch.quazz.caverna.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;
import ch.quazz.caverna.widget.CountingInput;

public class WealthFragment extends Fragment {

    final static class ItemCount {
        final int id;
        final GameItem item;

        ItemCount(int id, GameItem item) {
            this.id = id;
            this.item = item;
        }
    }

    private final ItemCount itemCounts[] = {
        new ItemCount(R.id.dwarfs, GameItem.Dwarfs),

        new ItemCount(R.id.dogs, GameItem.Dogs),
        new ItemCount(R.id.sheep, GameItem.Sheep),
        new ItemCount(R.id.donkeys, GameItem.Donkeys),
        new ItemCount(R.id.boars, GameItem.Boars),
        new ItemCount(R.id.cattle, GameItem.Cattle),

        new ItemCount(R.id.grain, GameItem.Grains),
        new ItemCount(R.id.vegetable, GameItem.Vegetables),
        new ItemCount(R.id.rubies, GameItem.Rubies),
        new ItemCount(R.id.gold, GameItem.Gold),
        new ItemCount(R.id.begging_markers, GameItem.BeggingMarkers),

        new ItemCount(R.id.small_pastures, GameItem.SmallPastures),
        new ItemCount(R.id.large_pastures, GameItem.LargePastures),
        new ItemCount(R.id.ore_mines, GameItem.OreMines),
        new ItemCount(R.id.ruby_mines, GameItem.RubyMines),
        new ItemCount(R.id.unused_tiles, GameItem.UnusedTiles)
    };

    private PlayerScore playerScore;

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

        CountingInput.OnCountChangeListener listener = new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(CountingInput input, int count, boolean fromUser) {
                if (fromUser) {
                    playerScore.setCount((GameItem)input.getTag(), count);
                }
            }
        };

        for (final ItemCount itemCount : itemCounts) {
            CountingInput countingInput = (CountingInput)getActivity().findViewById(itemCount.id);
            countingInput.setTag(itemCount.item);
            countingInput.setCount(playerScore.getCount(itemCount.item));
            countingInput.addOnCountChangeListener(listener);
        }
    }
}
