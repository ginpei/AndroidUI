package info.ginpei.androidui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        message("Service is started.");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        message("Service is stopped. Bye!");
        super.onDestroy();
    }

    private void message(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
