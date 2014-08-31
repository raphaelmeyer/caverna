package ch.quazz.caverna.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.ScoreSheet;

public class ScoringPadAdapter extends BaseAdapter {

    private final Context context;
    private List<ScoreSheet> scoringPad;

    public ScoringPadAdapter(final Context context) {
        this.context = context;
        this.scoringPad = null;
    }

    void setScoringPad(final List<ScoreSheet> scoringPad) {
        this.scoringPad = scoringPad;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (scoringPad == null) {
            return 0;
        }
        return scoringPad.size();
    }

    @Override
    public Object getItem(int position) {
        if (scoringPad == null) {
            return null;
        }
        return scoringPad.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (scoringPad == null) {
            return 0;
        }
        return scoringPad.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = new TextView(context);
        } else {
            view = (TextView)convertView;
        }

        view.setText(String.valueOf(position + 1) + " Name");

        return view;
    }

}
