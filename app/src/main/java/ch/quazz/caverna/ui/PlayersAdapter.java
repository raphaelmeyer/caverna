package ch.quazz.caverna.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.quazz.caverna.R;
import ch.quazz.caverna.games.Player;

class PlayersAdapter extends BaseAdapter {
    private final Context context;
    private List<Player> players;

    PlayersAdapter(final Context context) {
        this.context = context;
        this.players = null;
    }

    void setPlayers(final List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (players == null) {
            return 0;
        }
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        if (players == null) {
            return null;
        }
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (players == null) {
            return 0;
        }
        return players.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }

        TextView title = view.findViewById(R.id.item_text);
        title.setText(players.get(position).name);

        return view;
    }
}
