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
import ch.quazz.caverna.games.Game;

public class GamesAdapter extends BaseAdapter {

    private final Context context;
    private List<Game> games;

    public GamesAdapter(final Context context) {
        this.context = context;
        this.games = null;
    }

    void setGames(final List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (games == null) {
            return 0;
        }
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        if (games == null) {
            return null;
        }
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (games == null) {
            return 0;
        }
        return games.get(position).id;
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

        String timestamp = DateUtils.formatDateTime(context, games.get(position).timestamp, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
        TextView title = (TextView)view.findViewById(R.id.item_text);
        title.setText(timestamp);

        return view;
    }
}
