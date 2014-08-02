package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreInventory extends Fragment {

    private Cattle cattle;

    public ScoreInventory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (cattle == null) {
            cattle = new Cattle();
        }

        if (savedInstanceState != null) {
            cattle.setDogs(savedInstanceState.getInt("dogs"));
            cattle.setSheep(savedInstanceState.getInt("sheep"));
        }
        return inflater.inflate(R.layout.fragment_score_inventory, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("dogs", cattle.dogs());
        outState.putInt("sheep", cattle.sheep());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();

        CountingInput dogs = (CountingInput)getActivity().findViewById(R.id.dogs);
        CountingInput sheep = (CountingInput)getActivity().findViewById(R.id.sheep);

        cattle.setDogs(dogs.getCount());
        cattle.setSheep(sheep.getCount());
    }

    @Override
    public void onResume() {
        super.onResume();

        CountingInput dogs = (CountingInput)getActivity().findViewById(R.id.dogs);
        CountingInput sheep = (CountingInput)getActivity().findViewById(R.id.sheep);

        dogs.setCount(cattle.dogs());
        sheep.setCount(cattle.sheep());
    }
}
