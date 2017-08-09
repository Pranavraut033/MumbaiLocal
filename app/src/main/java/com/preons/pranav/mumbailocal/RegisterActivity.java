package com.preons.pranav.mumbailocal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.preons.pranav.util.DBHelper;

import static com.preons.pranav.util.Constants.FIELD_ERR;
import static com.preons.pranav.util.Constants.PASS;
import static com.preons.pranav.util.Constants.PASS_ERR;
import static com.preons.pranav.util.Constants.PASS_LENGTH;
import static com.preons.pranav.util.Constants.USER;
import static com.preons.pranav.util.Constants.USER_ERR;
import static com.preons.pranav.util.Constants.VERSION;
import static com.preons.pranav.util.Constants.editor;

/**
 * Created on 21-03-17 at 19:38 by Pranav Raut.
 * For MumbaiLocal
 */

public class RegisterActivity extends AppCompatActivity {

    int[] editTextIDs = new int[]{
            R.id.full_name, R.id.username, R.id.password, R.id.email, R.id.number};
    TextInputEditText[] editTexts = new TextInputEditText[editTextIDs.length];
    String[] allInfo = new String[editTexts.length];
    private boolean[] b = new boolean[2];
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        for (int i = 0; i < editTextIDs.length; i++)
            editTexts[i] = (TextInputEditText) findViewById(editTextIDs[i]);
        dbHelper = new DBHelper(this);
        intiET();
    }

    private void intiET() {
        editTexts[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = editTexts[1].getText().toString();
                if (b[0] = s1.isEmpty())
                    setErr(FIELD_ERR, editTexts[1]);
                else if (b[0] = s1.length() < 5)
                    setErr(USER_ERR, editTexts[1]);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        editTexts[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = editTexts[2].getText().toString();
                if (b[1] = s1.isEmpty())
                    setErr(FIELD_ERR, editTexts[2]);
                else if (b[1] = s1.length() < PASS_LENGTH)
                    setErr(PASS_ERR, editTexts[2]);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setErr(String s, TextInputEditText editText) {
        editText.setError(s);
    }

    public void done(View view) {
        switch (view.getId()) {
            case R.id.reg_done:
                if (!b[0] && !b[1]) {
                    int i = 0;
                    for (TextInputEditText t : editTexts)
                        allInfo[i++] = t.getText().toString();
                    Cursor cursor = dbHelper.getData(allInfo[1]);
                    if (cursor.moveToFirst()) {
                        Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dbHelper.insertUser(allInfo[0], allInfo[1], allInfo[4], allInfo[3], allInfo[2], 2000);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(USER, allInfo[1]);
                    resultIntent.putExtra(PASS, allInfo[2]);
                    if (editor != null) {
                        editor.putInt(VERSION, dbHelper.numberOfRows() + 1);
                        editor.apply();
                    }
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                break;
        }
    }
}
