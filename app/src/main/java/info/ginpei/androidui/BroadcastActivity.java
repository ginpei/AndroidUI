/**
 * @see https://developer.android.com/guide/components/broadcasts.html
 */

package info.ginpei.androidui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BroadcastActivity extends AppCompatActivity {

    PowerConnectionReceiver receiver = new PowerConnectionReceiver();
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        statusTextView = (TextView) findViewById(R.id.textView_status);
    }

    @Override
    protected void onResume() {
        super.onResume();

        receiver.setListener(new PowerConnectionReceiver.OnReceiveListener() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status;
                String action = intent.getAction();
                if (action == "android.intent.action.ACTION_POWER_CONNECTED") {
                    status = "connected";
                } else {
                    status = "disconnected";
                }
                statusTextView.setText("Power is " + status);
            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(receiver);
    }
}
