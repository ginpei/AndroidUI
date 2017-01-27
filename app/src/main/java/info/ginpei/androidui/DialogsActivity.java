package info.ginpei.androidui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        ((Button) findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOkDialog();
            }
        });

        ((Button) findViewById(R.id.button_ok_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOkCancelDialog();
            }
        });

        ((Button) findViewById(R.id.button_custom)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog();
            }
        });
    }

    private void openOkDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Hi");
        dialog.setMessage("What's up man?\nThis is a simple dialog.");

        dialog.setPositiveButton(android.R.string.ok, null);

        dialog.create().show();
    }

    private void openOkCancelDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Confirm");
        dialog.setMessage("Are you sure to do something important?");

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "The task has been done.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton(android.R.string.cancel, null);

        dialog.create().show();
    }

    private void openCustomDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Add");
        dialog.setView(R.layout.layout_custom_dialog);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                EditText nameEditText = (EditText) alertDialog.findViewById(R.id.editText_name);
                String name = nameEditText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Canceled since required items are not given.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), name + " has been created.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton(android.R.string.cancel, null);

        dialog.create().show();
    }
}
