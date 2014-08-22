package ch.quazz.caverna.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.quazz.caverna.games.Game;

public class GamesAdapter extends BaseAdapter {

    private final Context context;
    private final List<Game> games;

    public GamesAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView)convertView;
        }

        String title = DateUtils.formatDateTime(context, games.get(position).timestamp, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
        textView.setText(title);

        return textView;
    }
}
