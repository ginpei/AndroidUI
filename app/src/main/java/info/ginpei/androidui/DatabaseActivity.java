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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    public static final String TAG = "DatabaseActivity";
    private UserReaderDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

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

        List itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserEntry._ID));
            itemIds.add(id);
        }
        cursor.close();

        Toast.makeText(this, "Read IDs: " + itemIds.toString(), Toast.LENGTH_SHORT).show();
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
