package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.quazz.caverna.score.GameItem;
import ch.quazz.caverna.score.PlayerScore;
import ch.quazz.caverna.R;
import ch.quazz.caverna.widget.CountingInput;

public class ScoreLandscape extends Fragment {
    private final ItemCountController.ItemCount itemCounts[] = {
            new ItemCountController.ItemCount(R.id.small_pastures, GameItem.SmallPastures),
            new ItemCountController.ItemCount(R.id.large_pastures, GameItem.LargePastures)
    };

    private PlayerScore playerScore;
    private ItemCountController itemCount;

    public ScoreLandscape() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_landscape, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        itemCount = new ItemCountController(playerScore, itemCounts, getActivity());
    }
}
