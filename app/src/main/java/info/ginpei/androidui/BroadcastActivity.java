/**
 * @see https://developer.android.com/guide/components/broadcasts.html
 */

package info.ginpei.androidui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class BroadcastActivity extends AppCompatActivity {

    LocalPowerConnectionReceiver receiver = new LocalPowerConnectionReceiver();
    private TextView statusTextView;
    private ImageView chargingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        statusTextView = (TextView) findViewById(R.id.textView_status);
        chargingImageView = (ImageView) findViewById(R.id.imageView_charging);
    }

    @Override
    protected void onResume() {
        super.onResume();

        receiver.setListener(new LocalPowerConnectionReceiver.OnReceiveListener() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                // run on UI thread to allow handle UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String action = intent.getAction();
                        BroadcastActivity.this.onReceive(action);
                    }
                });
            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(receiver, filter);
    }

    private void onReceive(String action) {
        String status;
        int bgColor;
        if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
            status = "connected";
            bgColor = android.R.color.holo_green_dark;
        } else if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
            status = "disconnected";
            bgColor = android.R.color.darker_gray;
        } else {
            Log.d("BroadcastActivity", action);
            status = null;
            bgColor = android.R.color.white;
        }

        String message;
        if (status != null) {
            message = "Power is " + status;
        } else {
            message = "Ready. Try plug on and off now.";
        }
        statusTextView.setText(message);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            chargingImageView.setBackgroundColor(getColor(bgColor));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(receiver);
    }

    public static class LocalPowerConnectionReceiver extends BroadcastReceiver {

        protected OnReceiveListener listener = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (listener != null) {
                listener.onReceive(context, intent);
            }
        }

        public void setListener(OnReceiveListener listener) {
            this.listener = listener;
        }

        public interface OnReceiveListener {
            void onReceive(Context context, Intent intent);
        }
    }
}
