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
    private SeekBar countSlider;

    public CountingInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        final LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counting_input, this);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountingInput);
        setup(attributes);
    }

    public void setCount(int count) {
        this.count = count;

        countSlider.setProgress(count - min);
        updateText();
    }

    private void setup(TypedArray attributes) {
        label = attributes.getString(R.styleable.CountingInput_label);
        min = attributes.getInteger(R.styleable.CountingInput_min, 0);
        max = attributes.getInteger(R.styleable.CountingInput_max, 100);

        count = min;

        countText = (TextView)findViewById(R.id.count_text);
        countSlider = (SeekBar)findViewById(R.id.count_slider);

        setupSeekbar();
    }

    private void setupSeekbar() {

        countSlider.setMax(max - min);
        countSlider.setProgress(count - min);
        updateText();

        countSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = min + progress;
                updateText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateText() {
        countText.setText(label + ": " + Integer.toString(count));
    }
}
