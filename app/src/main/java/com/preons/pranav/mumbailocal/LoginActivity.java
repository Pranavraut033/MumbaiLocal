package com.preons.pranav.mumbailocal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.preons.pranav.util.DBHelper;

import static com.preons.pranav.util.Constants.ADMIN;
import static com.preons.pranav.util.Constants.FIELD_ERR;
import static com.preons.pranav.util.Constants.FULL_NAME;
import static com.preons.pranav.util.Constants.PASS;
import static com.preons.pranav.util.Constants.PASS_ERR;
import static com.preons.pranav.util.Constants.PASS_LENGTH;
import static com.preons.pranav.util.Constants.REGISTER;
import static com.preons.pranav.util.Constants.REMEMBER;
import static com.preons.pranav.util.Constants.USER;
import static com.preons.pranav.util.Constants.USER_COLUMN_ID;
import static com.preons.pranav.util.Constants.USER_COLUMN_NAME;
import static com.preons.pranav.util.Constants.USER_COLUMN_PASS;
import static com.preons.pranav.util.Constants.USER_COLUMN_USERNAME;
import static com.preons.pranav.util.Constants.USER_ERR;
import static com.preons.pranav.util.Constants.USER_NAME;
import static com.preons.pranav.util.Constants.VERSION;
import static com.preons.pranav.util.Constants.dDime;
import static com.preons.pranav.util.Constants.editor;
import static com.preons.pranav.util.Constants.preferences;

/**
 * Created on 21-03-17 at 19:38 by Pranav Raut.
 * For MumbaiLocal
 */

public class LoginActivity extends AppCompatActivity {
    TextInputEditText editText;
    TextInputEditText editText1;
    DBHelper dbHelper;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_login));
        setSupportActionBar(toolbar);
        editText = (TextInputEditText) findViewById(R.id.username);
        editText1 = (TextInputEditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.rem_me);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        dbHelper = new DBHelper(this);
        dDime = getResources().getDimension(R.dimen.dPadding);
        if (preferences.getBoolean(REMEMBER, false)) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        }
        Cursor cursor = dbHelper.getData(0);
        int i = preferences.getInt(VERSION, 0);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(cursor.getColumnIndex(USER_COLUMN_NAME));
            if (!s.equals(ADMIN)) dbHelper.updateUser(0, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN,0);
        } else if (i == 0) dbHelper.insertUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN,0);
    }

    private boolean check(String s, String s1) {
        boolean b;
        if (b = s.isEmpty()) setErr1(FIELD_ERR);
        else if (b = s.length() < 5) setErr1(USER_ERR);
        else if (b = s1.isEmpty()) setErr2(USER_ERR);
        else if (b = s1.length() < PASS_LENGTH) setErr2(PASS_ERR);
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTER:
                if (resultCode == RESULT_OK) {
                    editText.setText(data.getStringExtra(USER));
                    editText1.setText(data.getStringExtra(PASS));
                }
                break;
        }
    }

    private void setErr2(String s) {
        editText1.setError(s);
    }

    private void setErr1(String s) {
        editText.setError(s);
    }

    public void clicks(View view) {
        switch (view.getId()) {
            case R.id.reg_button:
                startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER);
                break;
            case R.id.login_button:
                String u = editText.getText().toString(), p = editText1.getText().toString();
                if (check(u, p)) break;
                Cursor cursor = dbHelper.getData(u);
                if (cursor.moveToFirst()) {
                    String u1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME));
                    String p1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASS));
                    boolean b = u1.equals(u) && p1.equals(p);
                    if (!b){
                        Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
                        break;
                    }
                    else
                        startActivity(new Intent(this, Dashboard.class));
                    if (editor != null) {
                        editor.putString(FULL_NAME, cursor.getString(cursor.getColumnIndex(USER_COLUMN_NAME)));
                        editor.putBoolean(REMEMBER, checkBox.isChecked());
                        editor.putInt(USER_COLUMN_ID,cursor.getInt(cursor.getColumnIndex(USER_COLUMN_ID)));
                        editor.putString(USER_NAME,u);
                        editor.commit();
                    }
                    finish();
                } else
                    Toast.makeText(this, "Username not found", Toast.LENGTH_LONG).show();
                cursor.close();
                break;
        }
    }
}
