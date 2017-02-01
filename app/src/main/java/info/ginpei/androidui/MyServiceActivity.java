package info.ginpei.androidui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MyServiceActivity extends AppCompatActivity {

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
}
