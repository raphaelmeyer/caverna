package ch.quazz.caverna.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ch.quazz.caverna.score.Furnishing;
import ch.quazz.caverna.score.PlayerScore;

public class FurnishingsAdapter extends BaseAdapter {

    final static class Selection {
        final Furnishing furnishing;
        final int icon;

        Selection(Furnishing furnishing, int icon) {
            this.furnishing = furnishing;
            this.icon = icon;
        }
    }

    private final Context context;
    private final Selection[] furnishings;
    private final CompoundButton.OnCheckedChangeListener listener;

    private final PlayerScore playerScore;

    public FurnishingsAdapter(Context context, final Selection[] furnishings, CompoundButton.OnCheckedChangeListener listener, PlayerScore playerScore) {
        this.context = context;
        this.furnishings = furnishings;
        this.listener = listener;

        this.playerScore = playerScore;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        CheckBox checkBox;
        if (convertView == null) {
            int size = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, parent.getResources().getDisplayMetrics());

            Drawable icon = parent.getResources().getDrawable(furnishings[position].icon);
            icon.setBounds(0, 0, size, size);

            checkBox = new CheckBox(context);
            checkBox.setCompoundDrawables(icon, null, null, null);

            checkBox.setTag(furnishings[position].furnishing);
            checkBox.setChecked(playerScore.has(furnishings[position].furnishing));
            checkBox.setOnCheckedChangeListener(listener);

        } else {
            checkBox = (CheckBox)convertView;
        }

        return checkBox;
    }
}
