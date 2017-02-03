package info.ginpei.androidui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MultiThreadingActivity extends AppCompatActivity {

    public static final String ACTION_THREAD_START = "info.ginpei.androidui.MultiThreadingActivity.THREAD_START";
    public static final String ACTION_THREAD_DONE = "info.ginpei.androidui.MultiThreadingActivity.THREAD_DONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_threading);

        ((Button) findViewById(R.id.button_startThread)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });

        ((Button) findViewById(R.id.button_startAsyncTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask();
            }
        });

        ((Button) findViewById(R.id.button_startHandler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHandler();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_THREAD_START);
        filter.addAction(ACTION_THREAD_DONE);
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }

    private void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                sendBroadcast(new Intent(ACTION_THREAD_START));

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.setAction(ACTION_THREAD_DONE);
                intent.putExtra("message", "Hey it's done!");
                sendBroadcast(intent);
            }
        };

        thread.start();
    }

    private void onThreadStart(Intent intent) {
        System.out.println("Thread is started!");
    }

    private void onThreadDone(Intent intent) {
        String message = intent.getStringExtra("message");
        System.out.println("Thread is done! message=" + message);
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

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_THREAD_START:
                    onThreadStart(intent);
                    break;

                case ACTION_THREAD_DONE:
                    onThreadDone(intent);
                    break;
            }
        }
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
