package info.ginpei.androidui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

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
                String result = fetch(urlString);
                Log.d(TAG, "Fetched! result=" + result);
            }
        };

        thread.start();
    }

    private String fetch(String urlString) {
        String result = null;
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

            // read
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    Log.d(TAG, "line=" + line);
                    builder.append(line);
                    builder.append("\n");
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            result = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
