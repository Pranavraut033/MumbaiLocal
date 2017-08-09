package com.preons.pranav.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.preons.pranav.util.Constants.DATABASE_NAME1;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_AMOUNT;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_DATE;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_ID;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_METHOD;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_PASSENGERS;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_STATION_FOR;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_STATION_FROM;
import static com.preons.pranav.util.Constants.TRANS_COLUMN_STATION_TO;
import static com.preons.pranav.util.Constants.USER_COLUMN_USERNAME;

public class DBHelper2 extends SQLiteOpenHelper {

    private String tableName;

    public DBHelper2(Context context, String tableName, int version) {
        super(context, DATABASE_NAME1, null, version);
        this.tableName = tableName;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName +
                " (id integer primary key autoincrement, fromS text, toS text, forS text," +
                " amount text, method text, passenger text, date text)");
    }

    void createTable(String tableName) {
        getWritableDatabase().execSQL("create table " + tableName +
                " (id integer primary key autoincrement, fromS text, toS text, forS text," +
                " amount text, method text, passenger text, date text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRANS_COLUMN_METHOD);
        onCreate(db);
    }

    public boolean insertTransactions(String toS, String fromS, String forS, String amount,
                                      String method, String passengers, String date) {
        getWritableDatabase().insert(tableName, null, contentVal(toS, fromS, forS, amount,
                method, passengers, date));
        return true;
    }

    private ContentValues contentVal(String toS, String fromS, String forS, String amount,
                                     String method, String passengers, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANS_COLUMN_STATION_TO, toS);
        contentValues.put(TRANS_COLUMN_STATION_FROM, fromS);
        contentValues.put(TRANS_COLUMN_STATION_FOR, forS);
        contentValues.put(TRANS_COLUMN_AMOUNT, amount);
        contentValues.put(TRANS_COLUMN_METHOD, method);
        contentValues.put(TRANS_COLUMN_PASSENGERS, passengers);
        contentValues.put(TRANS_COLUMN_DATE, date);
        return contentValues;
    }

    public Cursor getData(String s) {
        return getReadableDatabase().rawQuery("select * from " + tableName + " where " +
                USER_COLUMN_USERNAME + "= '" + s + "'", null);
    }

    public int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(getReadableDatabase(), tableName);
    }

    public boolean updateTransactions(Integer id, String toS, String fromS, String forS,
                                      String amount, String method, String passengers, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName, contentVal(toS, fromS, forS, amount, method, passengers, date),
                "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public String[][] getEverything(){
        //hp = new HashMap();
        String[] strings = new String[]{
                TRANS_COLUMN_ID, TRANS_COLUMN_STATION_TO, TRANS_COLUMN_STATION_FROM,
                TRANS_COLUMN_STATION_FOR, TRANS_COLUMN_AMOUNT, TRANS_COLUMN_METHOD,
                TRANS_COLUMN_PASSENGERS, TRANS_COLUMN_DATE
        };
        String[][] temp = new String[strings.length][numberOfRows()];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + tableName, null);
        for (int i = 0; i < strings.length; i++) {
            res.moveToFirst();
            int j = 0;
            while (!res.isAfterLast() && j < numberOfRows()) {
                temp[i][j++] = res.getString(res.getColumnIndex(strings[i]));
                res.moveToNext();
            }
        }
        res.close();
        return temp;
    }


}