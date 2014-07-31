package ch.quazz.caverna;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
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

        setOrientation(LinearLayout.VERTICAL);
//        android:orientation="vertical"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:layout_marginBottom="2dp"
//        android:layout_marginTop="2dp">

        final LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counting_input, this, true);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountingInput);
        setup(attributes);
        attributes.recycle();
    }

    public void setCount(int count) {
        this.count = count;

        countSlider.setProgress(count - min);
        updateText();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable instanceState = super.onSaveInstanceState();

        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        bundle.putParcelable("instanceState", instanceState);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            count = bundle.getInt("count");

            countSlider.setProgress(count - min);
            updateText();

            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
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
