package info.ginpei.androidui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_text_to_speech:
                startActivity(new Intent(this, TextToSpeechActivity.class));
                break;

            case R.id.button_spinner:
                startActivity(new Intent(this, SpinnerActivity.class));
                break;

            case R.id.button_references:
                startActivity(new Intent(this, PreferencesActivity.class));
                break;
        }
    }
}
