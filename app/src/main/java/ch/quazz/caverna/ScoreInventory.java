package ch.quazz.caverna;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ScoreInventory extends Fragment {

    private View view;
    private List<CountListener> countListeners;

    private class CountListener {
        public CountListener(CountingInput.OnCountChangeListener listener, int id) {
            this.listener = listener;
            this.id = id;
        }
        public CountingInput.OnCountChangeListener listener;
        public int id;
    }

    public ScoreInventory() {
    }

    public void addCountListener(CountingInput.OnCountChangeListener listener, int id) {
        if (view == null) {
            if (countListeners == null) {
                countListeners = new ArrayList<CountListener>();
            }
            countListeners.add(new CountListener(listener, id));
        } else {
            CountingInput input = (CountingInput)view.findViewById(id);
            input.addOnCountChangeListener(listener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_inventory, container, false);

        if (countListeners != null) {
            for (CountListener countListener : countListeners) {
                CountingInput input = (CountingInput)view.findViewById(countListener.id);
                input.addOnCountChangeListener(countListener.listener);
            }
            countListeners = null;
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
