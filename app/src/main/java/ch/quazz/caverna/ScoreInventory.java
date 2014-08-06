package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreInventory extends Fragment {

    private PlayerScore playerScore;

    private final class Input {
        public final int id;
        public final PlayerScore.Item item;

        public Input(int id, PlayerScore.Item item) {
            this.id = id;
            this.item = item;
        }
    };

    private final Input inputs[] = {
        new Input(R.id.dogs, PlayerScore.Item.Dogs),
        new Input(R.id.sheep, PlayerScore.Item.Sheep)
    };

    public ScoreInventory() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_inventory, container, false);

        for (final Input input : inputs) {
            CountingInput inputView = (CountingInput)view.findViewById(input.id);
            inputView.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
                @Override
                public void onCountChanged(int count) {
                    playerScore.setCount(input.item, count);
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        for (final Input input : inputs) {
            CountingInput inputView = (CountingInput)getActivity().findViewById(input.id);
            inputView.setCount(playerScore.getCount(input.item));
        }
    }
}
