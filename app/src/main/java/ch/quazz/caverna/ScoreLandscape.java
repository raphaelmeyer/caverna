package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreLandscape extends Fragment {

    private PlayerScore playerScore;

    public ScoreLandscape() {
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_landscape, container, false);


        CountingInput smallPastures = (CountingInput)view.findViewById(R.id.small_pastures);
        smallPastures.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                playerScore.setCount(PlayerScore.Item.SmallPastures, count);
            }
        });

        CountingInput largePastures = (CountingInput)view.findViewById(R.id.large_pastures);
        largePastures.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                playerScore.setCount(PlayerScore.Item.LargePastures, count);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        CountingInput smallPastures = (CountingInput)getActivity().findViewById(R.id.small_pastures);
        CountingInput largePastures = (CountingInput)getActivity().findViewById(R.id.large_pastures);

        smallPastures.setCount(playerScore.getCount(PlayerScore.Item.SmallPastures));
        largePastures.setCount(playerScore.getCount(PlayerScore.Item.LargePastures));
    }
}
