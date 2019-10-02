package ch.quazz.caverna.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import ch.quazz.caverna.R;

public class CountingInput extends LinearLayout {

    private int count;
    private int min;
    private int max;
    private String label;

    private final TextView countText;
    private final SeekBar countSlider;

    private OnCountChangeListener listener;

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

        ImageView icon = findViewById(R.id.count_icon);
        icon.setImageDrawable(attributes.getDrawable(R.styleable.CountingInput_icon));

        attributes.recycle();

        count = min;

        countText = findViewById(R.id.count_text);
        countSlider = findViewById(R.id.count_slider);

        setupSeekbar();
    }

    public void setCount(int count) {
        this.count = count;

        countSlider.setProgress(this.count - min);
        updateText();
    }

    @SuppressWarnings("unused")
    public int getCount() {
        return count;
    }

    public interface OnCountChangeListener {
        void onCountChanged(CountingInput input, int count, boolean fromUser);
    }

    public void setOnCountChangeListener(OnCountChangeListener listener) {
        this.listener = listener;
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
                if (listener != null) {
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
        countText.setText(label + ": " + (count));
    }
}
