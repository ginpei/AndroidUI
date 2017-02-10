package info.ginpei.androidui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchHttpActivity extends AppCompatActivity {

    public static final String TAG = "FetchHttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_http);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    private void start() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String urlString = "https://www.wikipedia.org/";
                Log.d(TAG, "Start fetching... for " + urlString);
                fetch(urlString);
                Log.d(TAG, "Fetched!");
            }
        };

        thread.start();
    }

    private void fetch(String urlString) {
        try {
            // fetch
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in;
            try {
                in = new BufferedInputStream(connection.getInputStream());
            } finally {
                connection.disconnect();
            }

            // read (in low layer)
            final int cbufLength = 128;
            char[] cbuf = new char[cbufLength];
            InputStreamReader reader = new InputStreamReader(in);
            try {
                for (int i = 0; true; i++) {
                    int result = reader.read(cbuf, 0, cbufLength);
                    Log.d(TAG, "@" + (i * cbufLength) + " Read: " + String.valueOf(cbuf));
                    if (result < 0) {
                        break;
                    }
                }
            } catch (IOException e) {
                Log.d(TAG, "IOException at somewhere!");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
