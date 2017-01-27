package info.ginpei.androidui;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    public static final String TAG = "TextToSpeechActivity";
    public static final String MY_UTTERANCE_ID = "My Utterance ID";
    private TextToSpeech tts;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        statusTextView = (TextView) findViewById(R.id.textView_status);
        setStatusText("Ready.");
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
                        public void onStart(String utteranceId) {
                            Log.d("TextToSpeechActivity", "Start " + utteranceId);
                            setText("Speaking...");
                            toggle(true);
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            Log.d(TAG, "Done " + utteranceId);
                            setText("Done.");
                            toggle(false);
                        }

                        @Override
                        public void onError(String utteranceId) {
                            Log.d(TAG, "Error " + utteranceId);
                            setText("Error!");
                            toggle(false);
                        }

                        /**
                         * A function which handles UI have to run on the UI thread.
                         * (But sometimes setting text can work?)
                         *
                         * @param text
                         */
                        private void setText(final String text) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setStatusText(text);
                                }
                            });
                        }

                        /**
                         * A function which handles UI have to run on the UI thread.
                         *
                         * @param speaking
                         */
                        private void toggle(final boolean speaking) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toggleSpeakingIcon(speaking);
                                }
                            });
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
        setStatusText("Initializing...");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // if utterance ID is not given (null), progress listener doesn't fire
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, MY_UTTERANCE_ID);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void setStatusText(String text) {
        statusTextView.setText(text);
    }

    private void toggleSpeakingIcon(boolean speaking) {
        int visibility = speaking ? View.VISIBLE : View.INVISIBLE;
        findViewById(R.id.imageView_speaking).setVisibility(visibility);
    }
}
