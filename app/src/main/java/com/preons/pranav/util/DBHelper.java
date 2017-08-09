package com.preons.pranav.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.preons.pranav.util.Constants.DATABASE_NAME;
import static com.preons.pranav.util.Constants.USER;
import static com.preons.pranav.util.Constants.USER_COLUMN_BAL;
import static com.preons.pranav.util.Constants.USER_COLUMN_EMAIL;
import static com.preons.pranav.util.Constants.USER_COLUMN_ID;
import static com.preons.pranav.util.Constants.USER_COLUMN_NAME;
import static com.preons.pranav.util.Constants.USER_COLUMN_PASS;
import static com.preons.pranav.util.Constants.USER_COLUMN_PHONE;
import static com.preons.pranav.util.Constants.USER_COLUMN_USERNAME;
import static com.preons.pranav.util.Constants.USER_TABLE_NAME;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + USER_TABLE_NAME +
                        " (id integer primary key autoincrement, name text," +
                        " username text, phone text, email text, pass text, bal integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_COLUMN_NAME);
        onCreate(db);
    }

    public boolean insertUser(String name, String username, String phone,
                              String email, String pass, int bal) {
        getWritableDatabase().insert(USER_TABLE_NAME, null, contentVal(name, username,
                phone, email, pass, bal));
        return true;
    }

    private ContentValues contentVal(String name, String username, String phone,
                                     String email, String pass, Integer bal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_PHONE, phone);
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_PASS, pass);
        contentValues.put(USER_COLUMN_BAL, bal);
        return contentValues;
    }

    public Cursor getData(String userName) {
        return getReadableDatabase().rawQuery("select * from " + USER_TABLE_NAME + " where " +
                USER_COLUMN_USERNAME + "= '" + userName + "'", null);
    }

    public Cursor getData(int ID) {
        return getReadableDatabase().rawQuery("select * from " + USER_TABLE_NAME + " where " +
                USER_COLUMN_ID + " = '" + ID + "'", null);
    }

    public int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(getReadableDatabase(), USER_TABLE_NAME);
    }

    public boolean updateUser(Integer id, String name, String username, String phone,
                              String email, String pass, int bal) {
        getWritableDatabase().update(USER_TABLE_NAME, contentVal(name, username, phone,
                email, pass, bal), "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateUser(Integer id, int bal) {
        Cursor cursor = getData(id);
        boolean b = cursor.moveToFirst();
        if (b) {
            updateUser(id, getString(USER_COLUMN_NAME, cursor),
                    getString(USER_COLUMN_NAME, cursor),
                    getString(USER_COLUMN_NAME, cursor),
                    getString(USER_COLUMN_NAME, cursor),
                    getString(USER_COLUMN_NAME, cursor),
                    bal);
        }
        return b;
    }

    private String getString(String columnName, Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public Integer deleteUser(Integer id) {

        return getWritableDatabase().delete(USER_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public int getBal(int id) {
        Cursor cursor = getData(id);
        return cursor.moveToFirst() ? cursor.getInt(cursor.getColumnIndex(USER_COLUMN_BAL)) : 0;
    }
}