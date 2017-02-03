package info.ginpei.androidui;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayMusicActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mediaPlayer.stop();
    }
}
