package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.Statics;

public class OtherNotificationsDatabase extends SQLiteOpenHelper {
    public static String title = "title";
    public static String DATABASE_NAME = "data.db";
    public static String state = "state";
    public static String table = "other";
    public static String date = "dat";
    CacheHelper statics;
    private static final String SQL_NETWORK_TABLE =
            "CREATE TABLE IF NOT EXISTS other ( id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT , dat TEXT , state TEXT, row_id TEXT)";

    public OtherNotificationsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        Create();
        statics = new CacheHelper(context);
    }

    public void delOldData() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.delete("other", null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_NETWORK_TABLE);
    }

    public void Create() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_NETWORK_TABLE);
    }

    public int getNewCount() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("Select * from other where state = '1'", null);
            return res.getCount();
        } catch (Exception e) {
            return 0;
        }

    }

    public void SetStateToOld() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(("UPDATE other SET state = '0' WHERE state = '1'"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS other");
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from other", null);
        return res;
    }

    public Cursor Search(String row_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from other where row_id = '" + row_id + "'", null);
        return res;
    }

    public boolean insertData(String titlew, String datew, String row_id) {
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(title, titlew.trim());
            contentValues.put(date, datew.trim());
            contentValues.put(state, "1");
            contentValues.put("row_id", row_id);
            resa = db.insert(table, null, contentValues);
            db.close();
        } catch (SQLException e) {
            return false;
        }
        return resa != -1;
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("delete from other where row_id = '" + id + "'", null);
    }
}
