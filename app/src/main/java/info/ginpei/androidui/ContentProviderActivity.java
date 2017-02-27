package info.ginpei.androidui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContentProviderActivity extends AppCompatActivity {

    private final String[] projection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        final ArrayList<Contact> contacts = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                contacts.add(buildContactByCursor(cursor));
            }

            cursor.close();
        }

        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                contacts
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Contact contact = contacts.get(position);
                String text1 = contact.displayName;
                String text2;
                if (contact.hasPhoneNumber) {
                    text2 = "";
                } else {
                    text2 = "No phone numbers.";
                }

                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1)).setText(text1);
                ((TextView) view.findViewById(android.R.id.text2)).setText(text2);
                return view;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView_contacts);
        listView.setAdapter(adapter);
    }

    private Contact buildContactByCursor(Cursor cursor) {
        return new Contact(cursor.getString(0), (cursor.getInt(1) != 0));
    }

    class Contact {

        public String displayName;
        public boolean hasPhoneNumber;

        public Contact(String displayName, boolean hasPhoneNumber) {
            this.displayName = displayName;
            this.hasPhoneNumber = hasPhoneNumber;
        }
    }
}
