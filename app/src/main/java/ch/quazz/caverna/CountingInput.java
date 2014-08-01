package ch.quazz.caverna;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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

        LayoutInflater.from(context).inflate(R.layout.counting_input, this, true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int padding = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));

        setOrientation(LinearLayout.VERTICAL);
        setPadding(0, padding, 0, padding);
        setLayoutParams(layoutParams);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountingInput);

        label = attributes.getString(R.styleable.CountingInput_label);
        min = attributes.getInteger(R.styleable.CountingInput_min, 0);
        max = attributes.getInteger(R.styleable.CountingInput_max, 100);

        attributes.recycle();

        count = min;

        countText = (TextView)findViewById(R.id.count_text);
        countSlider = (SeekBar)findViewById(R.id.count_slider);

        setupSeekbar();
    }

    public void setCount(int count) {
        this.count = count;

        countSlider.setProgress(count - min);
        updateText();
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
