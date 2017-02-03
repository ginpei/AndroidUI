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
import android.widget.ProgressBar;
import android.widget.TextView;

public class MultiThreadingActivity extends AppCompatActivity {

    public static final String ACTION_THREAD_START = "info.ginpei.androidui.MultiThreadingActivity.THREAD_START";
    public static final String ACTION_THREAD_DONE = "info.ginpei.androidui.MultiThreadingActivity.THREAD_DONE";
    public static final String ACTION_ASYNC_TASK_START = "info.ginpei.androidui.MultiThreadingActivity.ASYNC_TASK_START";
    public static final String ACTION_ASYNC_TASK_DONE = "info.ginpei.androidui.MultiThreadingActivity.ASYNC_TASK_DONE";
    private Button startThreadButton;
    private Button startAsyncTaskButton;
    private Button startHandlerButton;
    private TextView statusTextView;
    private ProgressBar workingProgressBar;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_threading);

        statusTextView = (TextView) findViewById(R.id.textView_status);
        workingProgressBar = (ProgressBar) findViewById(R.id.progressBar_working);
        startThreadButton = (Button) findViewById(R.id.button_startThread);
        startAsyncTaskButton = (Button) findViewById(R.id.button_startAsyncTask);
        startHandlerButton = (Button) findViewById(R.id.button_startHandler);

        startThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });

        startAsyncTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncTask();
            }
        });

        startHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHandler();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_THREAD_START);
        filter.addAction(ACTION_THREAD_DONE);
        filter.addAction(ACTION_ASYNC_TASK_START);
        filter.addAction(ACTION_ASYNC_TASK_DONE);
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);

        setStatusText("Ready.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        receiver.clearAbortBroadcast();
        unregisterReceiver(receiver);
    }

    private void setStatusText(String text) {
        statusTextView.setText(text);
    }

    private void setWorking(boolean working) {
        workingProgressBar.setVisibility(working ? View.VISIBLE : View.INVISIBLE);

        boolean enabled = !working;
        startThreadButton.setEnabled(enabled);
        startAsyncTaskButton.setEnabled(enabled);
        startHandlerButton.setEnabled(enabled);
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

                sendBroadcast(new Intent(ACTION_THREAD_DONE));
            }
        };

        thread.start();
    }

    private void startAsyncTask() {
        int goal = 10;
        int interval = 500;
        MyAsyncTask task = new MyAsyncTask();
        task.execute(goal, interval);
    }

    private void startHandler() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Run now!");
                setStatusText("Handler is done!");
                setWorking(false);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);
        // handler.removeCallbacks(runnable);  // cancel

        setStatusText("Handler has been set. It do it later...");
        setWorking(true);

    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_THREAD_START:
                    setStatusText("Thread is working...");
                    setWorking(true);
                    break;

                case ACTION_THREAD_DONE:
                    String message = intent.getStringExtra("message");
                    setStatusText("Thread is done!");
                    setWorking(false);
                    break;

                case ACTION_ASYNC_TASK_START:
                    setStatusText("AsyncTask is working...");
                    setWorking(true);
                    break;

                case ACTION_ASYNC_TASK_DONE:
                    setStatusText("AsyncTask is done!");
                    setWorking(false);
                    break;
            }
        }
    }

    class MyAsyncTask extends AsyncTask<Integer, String, Long> {

        public static final String TAG = "MyAsyncTask";

        @Override
        protected void onPreExecute() {
            sendBroadcast(new Intent(ACTION_ASYNC_TASK_START));
            Log.d(TAG, "onPreExecute");
        }

        @Override
        protected Long doInBackground(Integer... params) {
            Integer goal = params[0];
            Integer interval = params[1];
            for (int i = 0; i < goal; i++) {
                try {
                    publishProgress("i=" + i);
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "doInBackground: Done!");

            return 0L;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String message = values[0];
            Log.d(TAG, "onProgressUpdate: " + message);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            sendBroadcast(new Intent(ACTION_ASYNC_TASK_DONE));
            Log.d(TAG, "onPostExecute");
        }
    }
}
