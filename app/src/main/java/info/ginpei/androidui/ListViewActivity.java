package info.ginpei.androidui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    final static User users[] = {
            new User("Alice", 10, "Nice guy."),
            new User("Bob", 22, "Cool guy."),
            new User("Charlie", 33, "Good guy."),
            new User("Dan", 44, "Great guy."),
            new User("Eve", 55, "Awesome guy."),
    };
    ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                users
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                User user = users[position];

                ((TextView) view.findViewById(android.R.id.text1)).setText(user.name);
                ((TextView) view.findViewById(android.R.id.text2)).setText(String.format("%s (%d)", user.description, user.age));

                return view;
            }
        };

        listView.setAdapter(adapter);
    }

    static class User {
        public String name;
        public int age;
        public String description;

        public User(String name, int age, String description) {
            this.name = name;
            this.age = age;
            this.description = description;
        }
    }
}
