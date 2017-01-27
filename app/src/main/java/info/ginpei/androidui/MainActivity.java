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
            case R.id.button_text_to_speech:
                startActivity(new Intent(this, TextToSpeechActivity.class));
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
        }
    }
}
