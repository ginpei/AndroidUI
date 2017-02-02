package info.ginpei.androidui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MultiThreadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_threading);

        ((Button) findViewById(R.id.button_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        ((Button) findViewById(R.id.button_startAsyncTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask();
            }
        });

        ((Button) findViewById(R.id.button_handler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHandler();
            }
        });
    }

    private void start() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 300000000; i++) {
                    if (i % 10000000 == 0) {
                        System.out.println(i);
                    }
                }
                System.out.println("Done!");
            }
        };

        thread.start();
    }

    private void startAsyncTask() {
        int goal = 10;
        int interval = 500;
        MyAsyncTask task = new MyAsyncTask();
        task.execute(goal, interval);

        System.out.println("Started");
    }

    private void startHandler() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Run now!");
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);
        // handler.removeCallbacks(runnable);  // cancel
        System.out.println("Do it later...");

    }

    class MyAsyncTask extends AsyncTask<Integer, String, Long> {

        public static final String TAG = "MyAsyncTask";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
        }

        @Override
        protected Long doInBackground(Integer... params) {
            Integer goal = params[0];
            Integer interval = params[1];
            for (int i = 0; i < goal; i++) {
                try {
                    System.out.println(i);
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Done!");
            publishProgress();

            return 0L;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate");
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.d(TAG, "onPostExecute");
        }
    }
}
