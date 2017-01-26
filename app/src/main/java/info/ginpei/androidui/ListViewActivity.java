package info.ginpei.androidui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    final static ArrayList<User> users = new ArrayList<>(Arrays.asList(
            new User("Alice", 10, "Nice guy."),
            new User("Bob", 22, "Cool guy."),
            new User("Charlie", 33, "Good guy."),
            new User("Dan", 44, "Great guy."),
            new User("Fred", 55, "Awesome guy."),
            new User("George", 55, "Awesome guy."),
            new User("Hin", 21, "A guy."),
            new User("Jim", 22, "A guy."),
            new User("Kim", 23, "A guy."),
            new User("Lin", 24, "A guy.")
    ));
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(createArrayAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailFromListViewActivity.class);
                User user = users.get(position);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                Toast.makeText(getApplicationContext(), user.name, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @NonNull
    private ArrayAdapter<User> createArrayAdapter() {
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                users
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                User user = users.get(position);

                ((TextView) view.findViewById(android.R.id.text1)).setText(user.name);
                ((TextView) view.findViewById(android.R.id.text2)).setText(String.format("%s (%d)", user.description, user.age));

                return view;
            }
        };

        return adapter;
    }
}
