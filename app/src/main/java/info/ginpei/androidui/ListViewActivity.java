package info.ginpei.androidui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    final static ArrayList<User> users = User.getDummyList();
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
                User user = users.get(position);
                startDetailActivity(user);
            }
        });

        // turn on when you want do something instead of showing the menu
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                User user = users.get(position);
//                Toast.makeText(getApplicationContext(), user.name, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        registerForContextMenu(listView);
    }

    private void startDetailActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), DetailFromListViewActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @NonNull
    private ArrayAdapter<User> createArrayAdapter() {
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_2,
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_view_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.detail:
                int position = (int) info.id;
                User user = users.get(position);
                startDetailActivity(user);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
