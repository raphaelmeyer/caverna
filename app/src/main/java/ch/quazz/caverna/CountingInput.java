package ch.quazz.caverna;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class CountingInput extends LinearLayout {

    private int count = 0;
    private int min = 0;
    private int max = 0;
    private String label = "";

    private TextView countText;

    public CountingInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        final LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counting_input, this);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountingInput);
        setup(attributes);
    }

    private void setup(TypedArray attributes) {
        label = attributes.getString(R.styleable.CountingInput_label);
        min = attributes.getInteger(R.styleable.CountingInput_min, 0);
        max = attributes.getInteger(R.styleable.CountingInput_max, 100);

        countText = (TextView)findViewById(R.id.count_text);
        updateCount(0);

        setupSeekbar();
    }

    private void setupSeekbar() {
        SeekBar seekBar = (SeekBar)findViewById(R.id.count_slider);

        seekBar.setMax(max - min);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateCount(int progress) {
        count = min + progress;
        countText.setText(label + ": " + Integer.toString(count));
    }
}
