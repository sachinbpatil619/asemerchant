package com.shimoga.asesolMerchant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CurrentUser.db";

    //USER TABLE UNIVERSAL FOR ALL
    public static final String USER_TABLE = "CURRENT_USER_TABLE";
    public static final String USER_COL_1 = "ID";
    public static final String USER_COL_2 = "PHONE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //user table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , PHONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
    }

    public boolean addUser(String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, s1);

        long result = db.insert(USER_TABLE, null, contentValues);
        db.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public int checkUser() {
        String countQuery = "SELECT  * FROM CURRENT_USER_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public String setUSer() {
        String countQuery = "SELECT  * FROM CURRENT_USER_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex(USER_COL_2));
            return data;
        }
        return null;
    }

    public void clearUser() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM CURRENT_USER_TABLE");
        db.execSQL(query);
    }

}
