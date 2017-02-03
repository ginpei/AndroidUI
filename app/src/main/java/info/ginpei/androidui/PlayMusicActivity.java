package info.ginpei.androidui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class PlayMusicActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ToggleButton playPauseToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        playPauseToggleButton = (ToggleButton) findViewById(R.id.button_playPause);

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

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void play() {
        mediaPlayer.start();
    }

    private void pause() {
        mediaPlayer.pause();
    }
}
