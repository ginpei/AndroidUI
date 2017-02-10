package info.ginpei.androidui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.List;

public class OrmaActivity extends AppCompatActivity {

    private static final String TAG = "G#OrmaActivity";

    private OrmaDatabase orma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orma);

        orma = OrmaDatabase.builder(getApplicationContext()).build();

        // read
        final List<Entry> entries = orma.selectFromEntry().toList();
        for (Entry e : entries) {
            Log.d(TAG, "Entry #" + e.id + ", " + e.title);
        }

        new Thread() {
            @Override
            public void run() {
                int length = entries.size();

                // create
                Entry newEntry = new Entry();
                if (length < 1) {
                    newEntry.title = "Hello World!";
                    newEntry.content = "Hi. This is my first entry.";
                } else {
                    newEntry.title = "Hey Mr." + (length + 1);
                    newEntry.content = "Hi there!!!!!!!!!!!!!!";
                }
                orma.insertIntoEntry(newEntry);

                // update
                if (length > 0) {
                    Entry lastEntry = entries.get(length - 1);
                    orma.updateEntry()
                            .idEq(lastEntry.id)
                            .content("updated (WOW!)")
                            .execute();
                }

                // delete
                if (length > 0) {
                    Entry lastEntry = entries.get(length - 1);
                    orma.deleteFromEntry()
                            .idEq(lastEntry.id)
                            .execute();
                }
            }
        }.start();
    }

    @Table
    static class Entry {
        @PrimaryKey(autoincrement = true)
        public long id;

        @Column(indexed = true)
        public String title;

        @Column
        @Nullable
        public String content;
    }
}
