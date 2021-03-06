package info.ginpei.androidui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    public static final String TAG = "PowerConnectionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String uri = intent.toUri(Intent.URI_INTENT_SCHEME).toString();
        log("Action: " + action + ", URI: " + uri);
    }

    public void log(String text) {
        Log.d(TAG, text);
    }
}
