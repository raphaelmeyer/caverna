package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ScoreInventory extends Fragment {

    private PlayerScore playerScore;

    public ScoreInventory() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_inventory, container, false);

        CountingInput dogs = (CountingInput)view.findViewById(R.id.dogs);
        dogs.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                playerScore.setCount(PlayerScore.Item.Dogs, count);
            }
        });

        CountingInput sheep = (CountingInput)view.findViewById(R.id.sheep);
        sheep.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                playerScore.setCount(PlayerScore.Item.Sheep, count);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        CountingInput dogs = (CountingInput)getActivity().findViewById(R.id.dogs);
        CountingInput sheep = (CountingInput)getActivity().findViewById(R.id.sheep);

        dogs.setCount(playerScore.getCount(PlayerScore.Item.Dogs));
        sheep.setCount(playerScore.getCount(PlayerScore.Item.Sheep));
    }
}
