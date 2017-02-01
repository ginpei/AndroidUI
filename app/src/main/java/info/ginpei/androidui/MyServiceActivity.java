package info.ginpei.androidui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MyServiceActivity extends AppCompatActivity {

    public static final int NOTIFY_ID = 0;
    public static final int REQUEST_CODE = 0;

    private Button stopServiceButton;
    private Button startServiceButton;
    private Button stopIntentServiceButton;
    private Button startIntentServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);

        startServiceButton = (Button) findViewById(R.id.button_start);
        stopServiceButton = (Button) findViewById(R.id.button_stop);
        startIntentServiceButton = (Button) findViewById(R.id.button_startIntent);
        stopIntentServiceButton = (Button) findViewById(R.id.button_stopIntent);
        Button notifyButton = (Button) findViewById(R.id.button_notify);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyService();
            }
        });
        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyService();
            }
        });

        startIntentServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyIntentService();
            }
        });
        stopIntentServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyIntentService();
            }
        });
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        startServiceButton.setEnabled(true);
        stopServiceButton.setEnabled(false);
        stopMyIntentService();
    }

    public void startMyService() {
        startService(new Intent(this, MyService.class));

        startServiceButton.setEnabled(false);
        stopServiceButton.setEnabled(true);
    }

    public void stopMyService() {
        stopService(new Intent(this, MyService.class));

        startServiceButton.setEnabled(true);
        stopServiceButton.setEnabled(false);
    }

    public void startMyIntentService() {
        startService(new Intent(this, MyIntentService.class));

        startIntentServiceButton.setEnabled(false);
        stopIntentServiceButton.setEnabled(true);
    }

    public void stopMyIntentService() {
        stopService(new Intent(this, MyIntentService.class));

        startIntentServiceButton.setEnabled(true);
        stopIntentServiceButton.setEnabled(false);
    }

    private void showNotification() {
        // show a small icon at the Android's top bar
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("MyService");
        builder.setContentText("The service has started!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);  // remove the notification when the user click it

        // appears popping up
        builder.setDefaults(Notification.DEFAULT_SOUND);  // set something so that you can make it pop up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        // call the activity when the user tap the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                REQUEST_CODE,
                new Intent(this, MyServiceActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(NOTIFY_ID, builder.build());
        } else {
            manager.notify(NOTIFY_ID, builder.getNotification());
        }
    }
}
