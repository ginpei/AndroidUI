package info.ginpei.androidui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PlayMusicActivity extends AppCompatActivity {

    public static final String ACTION_TICK = "info.ginpei.androidui.PlayMusicActivity.Ticker.TICK";
    public static final int TICKER_INTERVAL = 100;

    private MediaPlayer mediaPlayer;
    private ToggleButton playPauseToggleButton;
    private AsyncTask ticker;
    private BroadcastReceiver receiver;
    private boolean rickerRunning = false;
    private TextView progressTextView;
    private TextView durationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        buildTicker();

        playPauseToggleButton = (ToggleButton) findViewById(R.id.button_playPause);
        progressTextView = (TextView) findViewById(R.id.textView_progress);
        durationTextView = (TextView) findViewById(R.id.textView_duration);

        playPauseToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    play();
                } else {
                    pause();
                }
            }
        });

        updateCurrentPosition();
        updateDuration();
    }

    private void buildTicker() {
        initTicker();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(ACTION_TICK)) {
                    updateCurrentPosition();
                }
            }
        };
    }

    private void initTicker() {
        ticker = new AsyncTask() {
            @Override
            protected Void doInBackground(Object... values) {
                while (true) {
                    sendBroadcast(new Intent(ACTION_TICK));

                    try {
                        Thread.sleep(TICKER_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    private void play() {
        mediaPlayer.start();
        updateDuration();
        startTicker();
    }

    private void pause() {
        mediaPlayer.pause();
        stopTicker();
    }

    private void updateCurrentPosition() {
        int progress = mediaPlayer.getCurrentPosition();
        progressTextView.setText(String.valueOf(progress / 1000));
    }

    private void updateDuration() {
        int duration = mediaPlayer.getDuration();
        durationTextView.setText(String.valueOf(duration / 1000));
    }

    private void startTicker() {
        registerReceiver(receiver, new IntentFilter(ACTION_TICK));

        initTicker();
        ticker.execute();

        rickerRunning = true;
    }

    private void stopTicker() {
        if (rickerRunning) {
            unregisterReceiver(receiver);

            ticker.cancel(true);
            ticker = null;

            rickerRunning = false;
        }
    }
}
