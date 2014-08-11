package ch.quazz.caverna.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import ch.quazz.caverna.R;

public class FurnishingsAdapter extends BaseAdapter {

    private Context context;

    private final Integer[] icons;


    public FurnishingsAdapter(Context context, final Integer[] icons) {
        this.context = context;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return icons.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox checkBox;
        if (convertView == null) {
            int size = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, parent.getResources().getDisplayMetrics());

            Drawable icon = parent.getResources().getDrawable(icons[position]);
            icon.setBounds(0, 0, size, size);

            checkBox = new CheckBox(context);
            checkBox.setCompoundDrawables(icon, null, null, null);
        } else {
            checkBox = (CheckBox)convertView;
        }

        return checkBox;
    }
}
