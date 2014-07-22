package ch.quazz.caverna;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CountingInput extends LinearLayout {

    public CountingInput(Context context, AttributeSet attr) {
        super(context, attr);

        final LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counting_input, this);
    }

}
