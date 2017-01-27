package info.ginpei.androidui;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    public static final String TAG = "TextToSpeechActivity";
    public static final String MY_UTTERANCE_ID = "My Utterance ID";
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                            Log.d("TextToSpeechActivity", "Start " + s);
                        }

                        @Override
                        public void onDone(String s) {
                            Log.d(TAG, "Done " + s);
                        }

                        @Override
                        public void onError(String s) {
                            Log.d(TAG, "Error " + s);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        tts.stop();
        tts.shutdown();
        tts = null;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_speech:
                speak();
                break;
        }
    }

    public void speak() {
        String text = ((EditText) findViewById(R.id.editText_textToBeSpeeched)).getText().toString();
        speak(text);
    }

    public void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // if utterance ID is not given, progress listener doesn't fire
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, MY_UTTERANCE_ID);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
