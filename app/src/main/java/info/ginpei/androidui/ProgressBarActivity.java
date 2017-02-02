package info.ginpei.androidui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class ProgressBarActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String TAG = "ProgressBarActivity";
    private ProgressBar largeProgressBar;
    private ProgressBar normalProgressBar;
    private ProgressBar smallProgressBar;
    private ProgressBar horizontalProgressBar;
    private SeekBar normalSeekBar;
    private SeekBar discreteSeekBar;
    private TextView valueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        largeProgressBar = (ProgressBar) findViewById(R.id.progressBar_large);
        normalProgressBar = (ProgressBar) findViewById(R.id.progressBar_normal);
        smallProgressBar = (ProgressBar) findViewById(R.id.progressBar_small);
        horizontalProgressBar = (ProgressBar) findViewById(R.id.progressBar_horizontal);
        normalSeekBar = (SeekBar) findViewById(R.id.seekBar_normal);
        discreteSeekBar = (SeekBar) findViewById(R.id.seekBar_discrete);
        valueTextView = (TextView) findViewById(R.id.textView_value);

        normalSeekBar.setOnSeekBarChangeListener(this);
        discreteSeekBar.setOnSeekBarChangeListener(this);

        setMax(10);
        setValue(3);
    }

    private void setMax(int max) {
        largeProgressBar.setMax(max);
        normalProgressBar.setMax(max);
        smallProgressBar.setMax(max);
        horizontalProgressBar.setMax(max);
        normalSeekBar.setMax(max);
        discreteSeekBar.setMax(max);
    }

    private void setValue(int value) {
        final int max = 10;
        final int min = 0;

        if (value > max) {
            value = max;
        } else if (value < min) {
            value = min;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            largeProgressBar.setProgress(value, true);  // doesn't work though
            normalProgressBar.setProgress(value, true);  // doesn't work though
            smallProgressBar.setProgress(value, true);  // doesn't work though
            horizontalProgressBar.setProgress(value, true);
            normalSeekBar.setProgress(value, true);
            discreteSeekBar.setProgress(value, true);
        } else {
            largeProgressBar.setProgress(value);  // doesn't work though
            normalProgressBar.setProgress(value);  // doesn't work though
            smallProgressBar.setProgress(value);  // doesn't work though
            horizontalProgressBar.setProgress(value);
            normalSeekBar.setProgress(value);
            discreteSeekBar.setProgress(value);
        }

        String sValue = String.valueOf(value);
        Log.d(TAG, "valueTextView.getText().toString() = " + valueTextView.getText().toString());
        if (!valueTextView.getText().toString().equals(sValue)) {
            valueTextView.setText(sValue);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: Progress=" + progress + ", From User=" + fromUser);
        setValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch");
    }
}
