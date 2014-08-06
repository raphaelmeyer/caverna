package ch.quazz.caverna;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreInventory extends Fragment {

    private final ItemCountController.ItemCount itemCounts[] = {
        new ItemCountController.ItemCount(R.id.dogs, PlayerScore.Item.Dogs),
        new ItemCountController.ItemCount(R.id.sheep, PlayerScore.Item.Sheep)
    };

    private PlayerScore playerScore;
    private ItemCountController itemCount;

    public ScoreInventory() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_inventory, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        itemCount = new ItemCountController(playerScore, itemCounts, getActivity());
    }
}
