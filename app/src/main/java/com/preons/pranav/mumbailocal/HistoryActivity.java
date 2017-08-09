package com.preons.pranav.mumbailocal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.preons.pranav.util.DBHelper;
import com.preons.pranav.util.DBHelper2;

import static com.preons.pranav.util.Constants.ADMIN;
import static com.preons.pranav.util.Constants.FULL_NAME;
import static com.preons.pranav.util.Constants.USER_COLUMN_ID;
import static com.preons.pranav.util.Constants.USER_NAME;
import static com.preons.pranav.util.Constants.USER_TABLE_NAME;
import static com.preons.pranav.util.Constants.VERSION;
import static com.preons.pranav.util.Constants.preferences;

public class HistoryActivity extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    private DBHelper dbHelper;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textView = (TextView) findViewById(R.id.name);
        textView1 = (TextView) findViewById(R.id.balance);
        DBHelper2 helper2 = null;
        dbHelper = new DBHelper(this);
        if (preferences != null)
            helper2 = new DBHelper2(this, preferences.getString(USER_NAME, ADMIN),
                    preferences.getInt(VERSION, dbHelper.numberOfRows()));
        ListView listView = (ListView) findViewById(R.id.list);
        if (helper2 != null)
            listView.setAdapter(new CustomAdapter2(this, helper2.getEverything()));
        textView.setText(preferences.getString(FULL_NAME, "User"));
        if (preferences != null)
            id = preferences.getInt(USER_COLUMN_ID, 0);
        textView1.setText(String.valueOf(dbHelper.getBal(id)));
    }

    public void clicks(View view) {
        LinearLayout linearLayout = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final TextInputEditText editText = (TextInputEditText) linearLayout.findViewById(R.id.balance);
        final TextInputEditText editText1 = (TextInputEditText) linearLayout.findViewById(R.id.number);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(null);
        final ProgressDialog dialog1 = new ProgressDialog(linearLayout.getContext());
        dialog1.setMessage("Adding...");
        dialog1.setCancelable(false);
        builder.setView(linearLayout)
                .setTitle("Enter Amount")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText1.getText().toString().isEmpty()) {
                            editText1.setError("Field required");
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (editText.getText().toString().isEmpty()) {
                            editText.setError("Field required");
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                            return;
                        }
                        dialog1.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog1.dismiss();
                                if (dbHelper.updateUser(id, dbHelper.getBal(id) +
                                        Integer.parseInt(editText.getText().toString()))) {
                                    textView1.setText(String.valueOf(dbHelper.getBal(id)));
                                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, 5000);
                        dialog.dismiss();
                    }
                }).show();
    }
}
