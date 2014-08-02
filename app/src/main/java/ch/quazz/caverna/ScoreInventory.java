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

    public void setCattle(Cattle cattle) {
        this.cattle = cattle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_inventory, container, false);

        CountingInput dogs = (CountingInput)view.findViewById(R.id.dogs);
        CountingInput sheep = (CountingInput)view.findViewById(R.id.sheep);

        dogs.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                cattle.setDogs(count);
            }
        });

        sheep.addOnCountChangeListener(new CountingInput.OnCountChangeListener() {
            @Override
            public void onCountChanged(int count) {
                cattle.setSheep(count);
            }
        });

        return view;
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
