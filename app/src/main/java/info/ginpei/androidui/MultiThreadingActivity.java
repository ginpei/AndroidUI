package info.ginpei.androidui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiThreadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_threading);

        ((Button) findViewById(R.id.button_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 300000000; i++) {
                    if (i % 10000000 == 0) {
                        System.out.println(i);
                    }
                }
                System.out.println("Done!");
            }
        };

        thread.start();
    }
}
