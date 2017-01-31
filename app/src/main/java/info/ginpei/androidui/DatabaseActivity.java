/**
 * @see https://developer.android.com/training/basics/data-storage/databases.html
 */

package info.ginpei.androidui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    public static final String TAG = "DatabaseActivity";
    private UserReaderDbHelper userDbHelper;
    private ListView listView;
    private User.List users;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        listView = (ListView) findViewById(R.id.listView);

        initListView();

        findViewById(R.id.button_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

        findViewById(R.id.button_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
            }
        });

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        userDbHelper = new UserReaderDbHelper(getApplicationContext());
    }

    private void initListView() {
        users = new User.List();
        adapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                users
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                User user = users.get(position);
                ((TextView) view.findViewById(android.R.id.text1)).setText(user.name);
                String description = "ID:" + user.id + ". Age: " + user.age + ". Comment: " + user.comment;
                ((TextView) view.findViewById(android.R.id.text2)).setText(description);

                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    private void create() {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_NAME, "Alice");
        values.put(UserEntry.COLUMN_NAME_AGE, 11);
        values.put(UserEntry.COLUMN_NAME_COMMENT, "Hello Wonderland!");

        SQLiteDatabase dbWritable = userDbHelper.getWritableDatabase();
        long id = dbWritable.insert(UserEntry.TABLE_NAME, null, values);

        Toast.makeText(this, "Created widh ID " + id, Toast.LENGTH_SHORT).show();
    }

    private void read() {
        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_NAME_NAME,
                UserEntry.COLUMN_NAME_AGE,
                UserEntry.COLUMN_NAME_COMMENT,
        };

        String selection = UserEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = {"Alice"};
        String sortOrder = UserEntry.COLUMN_NAME_NAME + " DESC";

        SQLiteDatabase dbReadable = userDbHelper.getReadableDatabase();
        Cursor cursor = dbReadable.query(
                UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        users.update(cursor);
        adapter.notifyDataSetChanged();
        cursor.close();

        Toast.makeText(this, "Read (and updated the list below)", Toast.LENGTH_SHORT).show();
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_AGE, 99);

        String selection = UserEntry.COLUMN_NAME_NAME + " like ?";
        String[] selectionArgs = {"Alice"};

        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        db.update(
                UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Toast.makeText(this, "Updated ", Toast.LENGTH_SHORT).show();
    }

    private void delete() {
        String selection = UserEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {"Alice"};

        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        db.delete(UserEntry.TABLE_NAME, selection, selectionArgs);

        Toast.makeText(this, "Deleted ", Toast.LENGTH_SHORT).show();
    }

    static class User {
        long id;
        String name;
        int age;
        String comment;

        public User(long id, String name, int age, String comment) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.comment = comment;
        }

        static class List extends ArrayList<User> {
            public void update(Cursor cursor) {
                clear();

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserEntry._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_NAME));
                    int age = cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_AGE));
                    String comment = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_COMMENT));

                    User user = new User(id, name, age, comment);
                    add(user);
                }
            }
        }
    }

    static class UserReaderContract {

        private UserReaderContract() {
            // nobody can instantiate
        }
    }

    class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_COMMENT = "comment";
    }

    class UserReaderDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "UserReader.db";

        private static final String SQL_CREATE_ENTRIES =
                "create table " + UserEntry.TABLE_NAME + " (" +
                        UserEntry._ID + " integer primary key," +
                        UserEntry.COLUMN_NAME_NAME + " text," +
                        UserEntry.COLUMN_NAME_AGE + " integer," +
                        UserEntry.COLUMN_NAME_COMMENT + " text" +
                        ")";

        private static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + UserEntry.TABLE_NAME;

        public UserReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
