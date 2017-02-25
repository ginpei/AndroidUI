package info.ginpei.androidui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Getter;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;
import com.github.gfx.android.orma.exception.NoValueException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrmaActivity extends AppCompatActivity {

    private static final String TAG = "G#OrmaActivity";

    private OrmaDatabase orma;
    private ArrayList<Entry> entries;
    private ArrayAdapter<Entry> entryArrayAdapter;

    private ListView entryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orma);

        orma = OrmaDatabase.builder(getApplicationContext()).build();

        initEntryListView();

        findViewById(R.id.button_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertEntry();
            }
        });
        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEntries();
            }
        });
        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEntry();
            }
        });
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEntry();
            }
        });
    }

    private void initEntryListView() {
        entryListView = (ListView) findViewById(R.id.listView_entries);

        entries = new ArrayList<>();

        entryArrayAdapter = new ArrayAdapter<Entry>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                entries
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Entry entry = entries.get(position);

                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1)).setText("#" + entry.id + " " + entry.getTitle());
                String subText;
                if (entry.updatedAt != null) {
                    subText = entry.updatedAt.toString();
                } else {
                    subText = "(null)";
                }
                ((TextView) view.findViewById(android.R.id.text2)).setText(subText);
                return view;
            }
        };

        entryListView.setAdapter(entryArrayAdapter);
    }

    private void insertEntry() {
        final Entry entry = new Entry();
        entry.setTitle("Hello World!");

        Log.d(TAG, "Inserting...");
        new Thread() {
            @Override
            public void run() {
                orma.insertIntoEntry(entry);
                Log.d(TAG, "Done inserting.");
            }
        }.start();
    }

    private void selectEntries() {
        Log.d(TAG, "Selecting...");
        new Thread() {
            @Override
            public void run() {
                List<Entry> entryList = orma.selectFromEntry().toList();
                Log.d(TAG, "Done selecting...");

                entries.clear();
                entries.addAll(entryList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        entryArrayAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Updated the list.");
                    }
                });
            }
        }.start();
    }

    private void updateEntry() {
        final Entry lastEntry;

        try {
            lastEntry = getLastEntry();
        } catch (NoValueException ignored) {
            Log.d(TAG, "No more entries.");
            return;
        }

        lastEntry.setTitle("Updated!");
        lastEntry.updateUpdateAt();

        Log.d(TAG, "Updating...");
        new Thread() {
            @Override
            public void run() {
                orma.updateEntry()
                        .idEq(lastEntry.id)
                        .title(lastEntry.getTitle())
                        .updatedAt(lastEntry.updatedAt)
                        .execute();
                Log.d(TAG, "Done updating.");
            }
        }.start();
    }

    private void deleteEntry() {
        final Entry lastEntry;

        try {
            lastEntry = getLastEntry();
        } catch (NoValueException ignored) {
            Log.d(TAG, "No more entries.");
            return;
        }

        Log.d(TAG, "Deleting...");
        new Thread() {
            @Override
            public void run() {
                orma.deleteFromEntry()
                        .idEq(lastEntry.id)
                        .execute();
                Log.d(TAG, "Done deleting.");
            }
        }.start();
    }

    @NonNull
    private Entry getLastEntry() {
        return orma.selectFromEntry()
                .orderByIdDesc()
                .limit(1)
                .get(0);
    }

    @Table
    static class Entry {
        @PrimaryKey(autoincrement = true)
        public long id;

        @Column
        @Nullable
        public Date createdAt;

        @Column
        @Nullable
        public Date updatedAt;

        @Column(indexed = true)
        @NonNull
        private String title;

        @Getter
        public String getTitle() {
            return title;
        }

        @Setter
        public void setTitle(String title) {
            Log.d(TAG, "setTitle() called with: title = [" + title + "] <- " + this.title);
            this.title = title;
        }

        public Entry() {
            createdAt = updatedAt = new Date();
        }

        public Date updateUpdateAt() {
            return updatedAt = new Date();
        }
    }
}
