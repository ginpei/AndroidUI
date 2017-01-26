package info.ginpei.androidui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailFromListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_from_list_view);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        ((TextView) findViewById(R.id.textView_name)).setText(user.name);
        ((TextView) findViewById(R.id.textView_age)).setText(String.valueOf(user.age));
        ((TextView) findViewById(R.id.textView_description)).setText(user.description);
    }
}
