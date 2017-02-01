package info.ginpei.androidui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MyServiceActivity extends AppCompatActivity {

    private Button stopButton;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);

        startButton = (Button) findViewById(R.id.button_start);
        stopButton = (Button) findViewById(R.id.button_stop);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyService();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyService();
            }
        });

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public void startMyService() {
        startService(new Intent(this, MyService.class));

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stopMyService() {
        stopService(new Intent(this, MyService.class));

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
}
