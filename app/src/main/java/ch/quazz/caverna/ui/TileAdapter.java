package ch.quazz.caverna.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ch.quazz.caverna.score.Tile;

public class TileAdapter extends BaseAdapter {

    final static class Item {
        final Tile tile;
        final int icon;

        Item(Tile tile, int icon) {
            this.tile = tile;
            this.icon = icon;
        }
    }

    interface Check {
        boolean isSelected(Tile tile);
    }

    private final Context context;
    private final Check check;
    private final Item[] furnishings;
    private final CompoundButton.OnCheckedChangeListener listener;

    public TileAdapter(Context context, Check check, final Item[] furnishings, CompoundButton.OnCheckedChangeListener listener) {
        this.context = context;
        this.check = check;
        this.furnishings = furnishings;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return furnishings.length;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        CheckBox checkBox;
        if (convertView == null) {
            checkBox = new CheckBox(context);
        } else {
            checkBox = (CheckBox)convertView;
        }

        int iconSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, parent.getResources().getDisplayMetrics());

        Drawable icon = parent.getResources().getDrawable(furnishings[position].icon);
        icon.setBounds(0, 0, iconSize, iconSize);

        checkBox.setCompoundDrawables(icon, null, null, null);

        checkBox.setTag(furnishings[position].tile);
        checkBox.setChecked(check.isSelected(furnishings[position].tile));
        checkBox.setOnCheckedChangeListener(listener);

        return checkBox;
    }
}
