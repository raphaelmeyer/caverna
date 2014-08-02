package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreLandscape extends Fragment {

    private Homeboard homeboard;

    public ScoreLandscape() {
    }

    public void setHomeboard(Homeboard homeboard) {
        this.homeboard = homeboard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_landscape, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        CountingInput smallPastures = (CountingInput)getActivity().findViewById(R.id.small_pastures);
        CountingInput largePastures = (CountingInput)getActivity().findViewById(R.id.large_pastures);

        smallPastures.setCount(homeboard.smallPastures());
        largePastures.setCount(homeboard.largePastures());
    }}
