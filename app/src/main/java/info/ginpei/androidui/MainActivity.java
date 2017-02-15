package info.ginpei.androidui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preferences:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_textToSpeech:
                startActivity(new Intent(this, TextToSpeechActivity.class));
                break;

            case R.id.button_textToSpeechInThread:
                startActivity(new Intent(this, TextToSpeechInThreadActivity.class));
                break;

            case R.id.button_voice_recognition:
                startActivity(new Intent(this, VoiceRecognitionActivity.class));
                break;

            case R.id.button_spinner:
                startActivity(new Intent(this, SpinnerActivity.class));
                break;

            case R.id.button_listView:
                startActivity(new Intent(this, ListViewActivity.class));
                break;

            case R.id.button_customListView:
                startActivity(new Intent(this, CustomListViewActivity.class));
                break;

            case R.id.button_dialogs:
                startActivity(new Intent(this, DialogsActivity.class));
                break;

            case R.id.button_scrolling:
                startActivity(new Intent(this, ScrollingActivity.class));
                break;

            case R.id.button_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                break;

            case R.id.button_broadcast:
                startActivity(new Intent(this, BroadcastActivity.class));
                break;

            case R.id.button_progressBar:
                startActivity(new Intent(this, ProgressBarActivity.class));
                break;

            case R.id.button_service:
                startActivity(new Intent(this, MyServiceActivity.class));
                break;

            case R.id.button_multiThreading:
                startActivity(new Intent(this, MultiThreadingActivity.class));
                break;

            case R.id.button_playMusic:
                startActivity(new Intent(this, PlayMusicActivity.class));
                break;

            case R.id.button_fetchHttp:
                startActivity(new Intent(this, FetchHttpActivity.class));
                break;

            case R.id.button_orma:
                startActivity(new Intent(this, OrmaActivity.class));
                break;

            case R.id.button_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
        }
    }
}
