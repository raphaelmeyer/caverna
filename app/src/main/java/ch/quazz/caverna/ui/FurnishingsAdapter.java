package ch.quazz.caverna.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import ch.quazz.caverna.R;

public class FurnishingsAdapter extends BaseAdapter {

    private Context context;

    private final CaveFragment.FurnishingSelection[] furnishings;


    public FurnishingsAdapter(Context context, final CaveFragment.FurnishingSelection[] furnishings) {
        this.context = context;
        this.furnishings = furnishings;
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
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // controller.onSelectionChanged(buttonView.getTag(), isChecked);
                }
            });

        } else {
            checkBox = (CheckBox)convertView;
        }

        return checkBox;
    }
}
