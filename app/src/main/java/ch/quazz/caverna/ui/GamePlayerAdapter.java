package ch.quazz.caverna.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.quazz.caverna.R;
import ch.quazz.caverna.score.ScoreSheet;

class GamePlayerAdapter extends BaseAdapter {

    private final Context context;
    private List<ScoreSheet> scoringPad;

    public GamePlayerAdapter(final Context context) {
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
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }

        ScoreSheet sheet = scoringPad.get(position);
        TextView title = (TextView)view.findViewById(R.id.item_text);
        title.setText(String.valueOf(sheet.player) + " " + sheet.name);

        return view;
    }

}
