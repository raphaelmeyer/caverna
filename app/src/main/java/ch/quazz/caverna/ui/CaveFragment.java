package ch.quazz.caverna.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import ch.quazz.caverna.R;

public class CaveFragment extends Fragment {

    private static final Integer[] FurnishingsIcons = {
            R.drawable.dwelling, R.drawable.dwelling, R.drawable.dwelling,
            R.drawable.dwelling, R.drawable.simple_dwelling_1, R.drawable.simple_dwelling_2,
            R.drawable.mixed_dwelling, R.drawable.couple_dwelling, R.drawable.additional_dwelling,

            R.drawable.carpenter, R.drawable.stone_carver
    };


    public CaveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cave, container, false);

        GridView gridview = (GridView)view.findViewById(R.id.furnishings);
        gridview.setAdapter(new FurnishingsAdapter(getActivity(), FurnishingsIcons));

        //gridview = (GridView)view.findViewById(R.id.bonus_furnishings);
        //gridview.setAdapter(new FurnishingsAdapter(getActivity(), BonusFurnishingsIcons));

        return view;

        /*
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
}
