/**
 * See:
 * - https://developer.android.com/reference/android/speech/SpeechRecognizer.html#ERROR_AUDIO
 *
 * Required in AndroidManifest.xml:
 * - In `<manifest>`: `<uses-permission android:name="android.permission.RECORD_AUDIO" />`
 */

package info.ginpei.androidui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static android.speech.SpeechRecognizer.*;

public class VoiceRecognitionActivity extends AppCompatActivity {

    public static final String TAG = "VOICE_RECOGNITION";
    TextView statusTextView;
    TextView resultTextView;
    private SpeechRecognizer recognizer;
    private boolean listening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        statusTextView = (TextView) findViewById(R.id.textView_voiceRecognitionStatus);
        resultTextView = (TextView) findViewById(R.id.textView_voiceRecognitionResult);

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(new Listener());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_recognizeVoice:
                startRecognition();
                break;
        }
    }

    public void startRecognition() {
        if (listening) {
            listening = false;
            recognizer.stopListening();
        } else {
            listening = true;
            getPermission();

            setStatusText("start recognition");
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

            recognizer.startListening(intent);
        }
    }

    private void getPermission() {
        String permission = Manifest.permission.RECORD_AUDIO;

        int permittedStatus = ContextCompat.checkSelfPermission(this, permission);
        if (permittedStatus != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        }
    }

    public void setStatusText(String text) {
        Log.d(TAG, text);
        statusTextView.setText(text);
    }

    public void setResultText(String text) {
        Log.d(TAG, text);
        resultTextView.setText(text);
    }

    class Listener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            setStatusText("Waiting for your voice...");
        }

        @Override
        public void onBeginningOfSpeech() {
            setStatusText("OK listening...");
        }

        @Override
        public void onRmsChanged(float v) {
//            setStatusText("RMS changed");
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            setStatusText("Buffer received");
        }

        @Override
        public void onEndOfSpeech() {
            setStatusText("Recognizing...");
        }

        @Override
        public void onError(int error) {
            String message;
            switch (error) {
                case ERROR_NETWORK_TIMEOUT:
                case ERROR_NETWORK:
                    message = "Network error. Make sure the network is available.";
                    break;

                case ERROR_AUDIO:
                    message = "Audio error. Do you have available audio stuff?";
                    break;

                case ERROR_SERVER:
                    message = "Server error. Something went wrong...";
                    break;

                case ERROR_CLIENT:
                    message = "Client error. Something went wrong...";
                    break;

                case ERROR_SPEECH_TIMEOUT:
                    message = "Timed out. Did you speak enough loudly?";
                    break;

                case ERROR_NO_MATCH:
                    message = "No match. Did you speak enough clearly?";
                    break;

                case ERROR_RECOGNIZER_BUSY:
                    message = "Recognizer is busy.";
                    break;

                case ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "No permissions. Please allow the app to access to your mic.";
                    break;

                default:
                    message = "Unkown error: " + error;
            }

            setStatusText(message);
        }

        @Override
        public void onResults(Bundle result) {
            listening = false;
            setStatusText("Done.");

            String text = pickUpResultText(result);
            setResultText(text);
        }

        private String pickUpResultText(Bundle result) {
            ArrayList data = result.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text;
            if (data == null) {
                text = null;
            } else {
                text = (String) data.get(data.size() - 1);
            }
            return text;
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            setStatusText("Partial results");
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            setStatusText("Event");
        }
    }
}
