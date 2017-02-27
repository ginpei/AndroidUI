package info.ginpei.androidui;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        String text;
        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                builder
                        .append("- ")
                        .append(cursor.getString(0))
                        .append(", ")
                        .append(cursor.getString(1))
                        .append(", ")
                        .append(cursor.getString(2))
                        .append("\n");
            }
            text = builder.toString();

            cursor.close();
        } else {
            text = "(No contacts.)";
        }

        TextView textView = (TextView) findViewById(R.id.textView_text1);
        textView.setText(text);
    }
}
