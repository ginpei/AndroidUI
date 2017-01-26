package info.ginpei.androidui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewActivity extends AppCompatActivity {

    ArrayList<User> users = User.getDummyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(
                this,
                R.layout.layout_custom_list_view_item,
                R.id.textView_name,
                users
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                User user = users.get(position);
                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(R.id.textView_name)).setText(user.name);
                ((TextView) view.findViewById(R.id.textView_age)).setText(String.valueOf(user.age));
                ((TextView) view.findViewById(R.id.textView_description)).setText(user.description);
                ((ImageView) view.findViewById(R.id.imageView)).setImageDrawable(getDrawable(user));
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    private Drawable getDrawable(User user) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(user.imageId);
        } else {
            drawable = getResources().getDrawable(user.imageId);
        }
        return drawable;
    }
}
