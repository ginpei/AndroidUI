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

        ((Button) findViewById(R.id.button_custom_with_buttons)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialogWithButtons();
            }
        });
    }

    private void openOkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Hi");
        builder.setMessage("What's up man?\nThis is a simple dialog.");

        builder.setPositiveButton(android.R.string.ok, null);

        AlertDialog dialog = builder.create();
        dialog.show();
        // or: dialog = builder.show()
    }

    private void openOkCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to do something important?");

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "The task has been done.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.show();
    }

    private void openCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Add");
        builder.setView(R.layout.layout_custom_dialog);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
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

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.show();
    }

    private void openCustomDialogWithButtons() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Add");

        final View view = getLayoutInflater().inflate(R.layout.layout_custom_dialog_with_buttons, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();

        view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View okButton) {
                EditText nameEditText = (EditText) view.findViewById(R.id.editText_name);
                String name = nameEditText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Canceled since required items are not given.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), name + " has been created.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cancelButton) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
