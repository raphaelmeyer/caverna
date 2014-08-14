package ch.quazz.caverna.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.quazz.caverna.R;

public class CountingInput extends LinearLayout {

    private int count = 0;
    private int min = 0;
    private int max = 0;
    private String label = "";

    private final TextView countText;
    private final SeekBar countSlider;

    private final List<OnCountChangeListener> listeners;

    public CountingInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.counting_input, this, true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int padding = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics()));

        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(0, padding, 0, padding);
        setLayoutParams(layoutParams);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountingInput);

        label = attributes.getString(R.styleable.CountingInput_label);
        min = attributes.getInteger(R.styleable.CountingInput_min, 0);
        max = attributes.getInteger(R.styleable.CountingInput_max, 100);

        ImageView icon = (ImageView)findViewById(R.id.count_icon);
        icon.setImageDrawable(attributes.getDrawable(R.styleable.CountingInput_icon));

        attributes.recycle();

        count = min;

        countText = (TextView)findViewById(R.id.count_text);
        countSlider = (SeekBar)findViewById(R.id.count_slider);

        setupSeekbar();

        listeners = new ArrayList<OnCountChangeListener>();
    }

    public void setCount(int count) {
        this.count = count;

        countSlider.setProgress(this.count - min);
        updateText();
    }

    public int getCount() {
        return count;
    }

    public interface OnCountChangeListener {
        public abstract void onCountChanged(CountingInput input, int count, boolean fromUser);
    }

    public void addOnCountChangeListener(OnCountChangeListener listener) {
        listeners.add(listener);
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
                for (OnCountChangeListener listener : listeners) {
                    listener.onCountChanged(CountingInput.this, count, fromUser);
                }
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
