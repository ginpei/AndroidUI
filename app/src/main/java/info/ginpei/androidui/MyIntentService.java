package info.ginpei.androidui;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String ACTION = "info.ginpei.androidui.ACTION_FOOO";
    public static final String TAG = "MyIntentService";

    // a constructor with zero arguments is required (Wow!)
    public MyIntentService() {
        super("");
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "MyIntentService$onHandleIntent: Action=" + action);
    }
}
